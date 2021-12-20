package teamproject.lam_server.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import teamproject.lam_server.domain.User;

import static teamproject.lam_server.constants.SessionConstants.LOGIN_USER;

public class MainController {

    @ModelAttribute("loginUser")
    public User loginUser(@SessionAttribute(name = LOGIN_USER, required = false) User loginUser) {
        if (loginUser != null) return loginUser;
        else return null;
    }
}
