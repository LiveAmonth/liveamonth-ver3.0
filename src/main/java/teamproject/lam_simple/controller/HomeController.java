package teamproject.lam_simple.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;
import teamproject.lam_simple.constants.SessionConstants;
import teamproject.lam_simple.domain.CityInfo;
import teamproject.lam_simple.domain.User;
import teamproject.lam_simple.service.CityService;
import teamproject.lam_simple.service.UserService;

import java.util.Calendar;
import java.util.List;

import static teamproject.lam_simple.constants.CategoryConstants.*;
import static teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory.INTRO;
import static teamproject.lam_simple.constants.SessionConstants.LOGIN_USER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final CityService cityService;
    private final UserService userService;

    @ModelAttribute("cityInfos")
    public List<CityInfo> cityInfos() {
        return cityService.findCityInfoByCategory(INTRO);
    }

    @ModelAttribute("month")
    public Month month() {
        return Month.values()[Calendar.getInstance().get(Calendar.MONTH)];
    }

    @GetMapping("/")
    public String homeLogin(
            @SessionAttribute(name = LOGIN_USER, required = false) User loginUser, Model model) {
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }
        return "main/home";
    }

}
