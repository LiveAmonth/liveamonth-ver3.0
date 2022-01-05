package teamproject.lam_server.app.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamproject.lam_server.constants.CategoryConstants.MyPageCategory;
import teamproject.lam_server.constants.SessionConstants;
import teamproject.lam_server.controller.ContentsController;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.uploader.localUploader;
import teamproject.lam_server.app.user.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static teamproject.lam_server.constants.AttrConstants.*;
import static teamproject.lam_server.constants.PathConstants.*;
import static teamproject.lam_server.constants.PathConstants.MY_PAGE;
import static teamproject.lam_server.constants.SessionConstants.PROFILE_IMAGE_DIR;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyPageController extends ContentsController {

    private final UserService userService;
//    private final S3Uploader upLoader;
    private final localUploader uploader;

    @ModelAttribute("sideBarMenus")
    public List<Map<String, Object>> sideBarMenus() {
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(this.createMenuMap(MY_PAGE, ACCOUNT , Arrays.stream(MyPageCategory.values()).filter(t -> t.getCategory().equals(ACCOUNT)).toArray()));
        list.add(this.createMenuMap(MY_PAGE, COMMUNITY, Arrays.stream(MyPageCategory.values()).filter(t -> t.getCategory().equals(COMMUNITY)).toArray()));
        list.add(this.createMenuMap(MY_PAGE, INQUIRY, Arrays.stream(MyPageCategory.values()).filter(t -> t.getCategory().equals(INQUIRY)).toArray()));
        return list;
    }

    @GetMapping("/myPage")
    public String myPage() {
        return MY_PAGE_DIR+MY_PAGE;
    }

    @PostMapping("/editProfileImage")
    public String editProfileImage(@SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser, @RequestPart(FILE_NAME) MultipartFile mFile) throws Exception {
        log.info("fileNAme = {}",mFile.getOriginalFilename());
        if (loginUser.getImage() != null) {
            log.info("UserImageName = {}",loginUser.getImage());
            uploader.delete(PROFILE_IMAGE_DIR + loginUser.getImage());
        }
        String saveName = uploader.uploadProfile(loginUser.getLoginId(), PROFILE_IMAGE_DIR, mFile.getOriginalFilename(), mFile.getBytes());
        userService.editUserImage(loginUser.getId(), saveName);

        return RE_DIRECT_DIR+MY_PAGE;
    }


}
