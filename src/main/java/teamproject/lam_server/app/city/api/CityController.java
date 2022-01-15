package teamproject.lam_server.app.city.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.app.city.service.CityService;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityNames;
import teamproject.lam_server.controller.MainController;
import teamproject.lam_server.app.city.entity.CityInfo;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CityController extends MainController {
    private final CityService cityService;

    @GetMapping("cities/{city}")
    public Object getCity(@PathVariable CityNames city) {
        return cityService.getDataAboutCity(city);
    }

    @GetMapping("cities-menus")
    public Object getCityNames() {
        return CityNames.values();
    }

    @GetMapping("cities/intro")
    public List<CityInfo> cityInfos() {
        return cityService.findCityInfoByCategory(CategoryConstants.CityInfoCategory.INTRO);
    }

}
