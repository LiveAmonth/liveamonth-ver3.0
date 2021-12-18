package teamproject.lam_simple.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import teamproject.lam_simple.domain.User;

import static teamproject.lam_simple.constants.SessionConstants.LOGIN_USER;

public class MainController {

    @ModelAttribute("loginUser")
    public User loginUser(@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {
        if (loginUser != null) return loginUser;
        else return null;
    }
}
