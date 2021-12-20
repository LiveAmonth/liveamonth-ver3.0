package teamproject.lam_simple.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamproject.lam_simple.constants.CategoryConstants.MyPageCategory;
import teamproject.lam_simple.constants.SessionConstants;
import teamproject.lam_simple.domain.User;
import teamproject.lam_simple.dto.LoginForm;
import teamproject.lam_simple.service.LoginService;
import teamproject.lam_simple.service.UserService;
import teamproject.lam_simple.uploader.S3Uploader;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static teamproject.lam_simple.constants.AttrConstants.*;
import static teamproject.lam_simple.constants.PathConstants.MY_PAGE;
import static teamproject.lam_simple.constants.PathConstants.*;
import static teamproject.lam_simple.constants.SessionConstants.*;
import static teamproject.lam_simple.constants.SessionConstants.LOGIN_USER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MyPageController extends ContentsController {

    private final UserService userService;
    private final LoginService loginService;
    private final S3Uploader s3Uploader;

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
    public String editProfileImage(HttpSession session, @RequestPart(FILE_NAME) MultipartFile mFile) throws Exception {
        User user = (User) session.getAttribute(LOGIN_USER);
        if (user.getImage() != null) s3Uploader.delete("/" + PROFILE_IMAGE_DIR + user.getImage());
        String saveName = s3Uploader.uploadProfile(user.getLoginId(), PROFILE_IMAGE_DIR, mFile.getOriginalFilename(), mFile.getBytes());
        userService.editUserImage(user.getId(), saveName);

        session.setAttribute(LOGIN_USER, userService.find(user.getId()));
        return RE_DIRECT_DIR+MY_PAGE;
    }


}
