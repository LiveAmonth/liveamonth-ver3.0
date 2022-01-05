package teamproject.lam_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import teamproject.lam_server.constants.SessionConstants;
import teamproject.lam_server.app.user.domain.User;


@RestController
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String homeLogin(
            @SessionAttribute(name = SessionConstants.LOGIN_USER, required = false) User loginUser, Model model){
        if (loginUser != null) {
            model.addAttribute(SessionConstants.LOGIN_USER, loginUser);
        }
        return "main/home";
    }

}
