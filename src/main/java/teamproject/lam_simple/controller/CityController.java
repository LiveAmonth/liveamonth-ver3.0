package teamproject.lam_simple.controller;

import lombok.RequiredArgsConstructor;
import org.jboss.jandex.Main;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_simple.constants.CategoryConstants.CityNames;
import teamproject.lam_simple.domain.User;
import teamproject.lam_simple.service.CityService;

import static teamproject.lam_simple.constants.SessionConstants.LOGIN_USER;

@Controller
@RequiredArgsConstructor
@RequestMapping("/city")
public class CityController extends MainController {
    private final CityService cityService;

    @GetMapping
    public String city(@RequestParam CityNames menu, Model model) {
        model.addAttribute("menu", menu);
        model.addAttribute("cities", CityNames.values());
        model.addAttribute("cityInfos", cityService.findCityInfoByName(menu));

        return "/city/city";
    }


}
