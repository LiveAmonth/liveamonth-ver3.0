package teamproject.lam_simple.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.constants.CategoryConstants.AccountCategory;
import teamproject.lam_simple.constants.CategoryConstants.CommunityCategory;
import teamproject.lam_simple.constants.CategoryConstants.InquiryCategory;
import teamproject.lam_simple.constants.CategoryConstants.MyPageCategory;
import teamproject.lam_simple.constants.SessionConstants;
import teamproject.lam_simple.domain.User;
import teamproject.lam_simple.dto.FindPwForm;
import teamproject.lam_simple.dto.LoginForm;
import teamproject.lam_simple.service.LoginService;
import teamproject.lam_simple.service.UserService;
import teamproject.lam_simple.uploader.S3Uploader;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static teamproject.lam_simple.constants.SessionConstants.*;
import static teamproject.lam_simple.constants.SessionConstants.PROFILE_IMAGE_DIR;

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
        list.add(this.createMenuMap("account", AccountCategory.values()));
        list.add(this.createMenuMap("community", CommunityCategory.values()));
        list.add(this.createMenuMap("inquiry", InquiryCategory.values()));
        return list;
    }

    @GetMapping("/myPage")
    public String myPage(@RequestParam("menu") AccountCategory menu, Model model) {
        if (menu == AccountCategory.modify || menu == AccountCategory.drop) {
            model.addAttribute("menu", menu);
            return "myPage/reCheckPassword";
        }
        model.addAttribute("menu", menu);
        return "myPage/myPage";
    }

    @GetMapping("/myPage/reCheckPassword")
    public String reCheckPassword(@ModelAttribute("form") LoginForm form,@ModelAttribute AccountCategory menu) {
        return "myPage/recheckPassword";
    }

    @PostMapping("/myPage/reCheckPassword")
    public String reCheckPassword(@Valid @ModelAttribute("form") LoginForm form,
                                  BindingResult bindingResult,
                                  @ModelAttribute AccountCategory menu) {
        if (bindingResult.hasErrors()) return "myPage/reCheckPassword";

        User loginUser = loginService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            bindingResult.reject("noData");
            return "myPage/reCheckPassword";
        }
        return "redirect:/mypage";
    }


    @PostMapping("/editProfileImage")
    public String editProfileImage(HttpSession session, @RequestPart("fileName") MultipartFile mFile) throws Exception {
        User user = (User) session.getAttribute(LOGIN_USER);
        if (user.getImage() != null) s3Uploader.delete("/" + PROFILE_IMAGE_DIR + user.getImage());
        String saveName = s3Uploader.uploadProfile(user.getLoginId(), PROFILE_IMAGE_DIR, mFile.getOriginalFilename(), mFile.getBytes());
        userService.editUserImage(user.getId(), saveName);

        session.setAttribute(LOGIN_USER, userService.find(user.getId()));
        return "redirect:/myPage";
    }


}
