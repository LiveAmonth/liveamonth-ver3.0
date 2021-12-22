package teamproject.lam_server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.constants.AttrConstants;
import teamproject.lam_server.constants.CategoryConstants.CityNames;
import teamproject.lam_server.service.CityService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CityController extends MainController {
    private final CityService cityService;

    @GetMapping("cities/{city}")
    public Object getCity(@PathVariable CityNames city) {
        return cityService.getDataAboutCity(city);
    }

    @GetMapping("cities-menus")
    public Object getCityNames(){
        return CityNames.values();
    }
}
