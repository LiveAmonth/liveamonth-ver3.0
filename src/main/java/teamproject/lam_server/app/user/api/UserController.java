package teamproject.lam_server.app.user.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.constants.CategoryConstants.GenderTypes;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.UserForm;
import teamproject.lam_server.app.user.service.UserService;
import teamproject.lam_server.validator.SignUpFormValidator;

import javax.validation.Valid;

import static teamproject.lam_server.constants.CategoryConstants.EmailDomains;
import static teamproject.lam_server.constants.PathConstants.*;
import static teamproject.lam_server.constants.SessionConstants.LOGIN_USER;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final SignUpFormValidator signUpFormValidator;

    @GetMapping("loginUser")
    public User loginUser(@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {
        if (loginUser != null) return loginUser;
        else return null;
    }

    @ModelAttribute("genderTypes")
    public GenderTypes[] genderTypes() {
        return GenderTypes.values();
    }

    @ModelAttribute("emailDomains")
    public EmailDomains[] emailDomains() {
        return EmailDomains.values();
    }


    @GetMapping("/signUp")
    public String signUp(@ModelAttribute(USER_FORM) UserForm userForm) {
        return USER_DIR + SIGN_UP;
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute(USER_FORM) UserForm userForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return USER_DIR + SIGN_UP;
        signUpFormValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) return USER_DIR + SIGN_UP;
        userService.save(userForm);
        return "redirect:/";
    }
}
