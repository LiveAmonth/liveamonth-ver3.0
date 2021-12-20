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

    @GetMapping("city")
    public Object city(@RequestParam CityNames menu) {
        Map<String, Object> map = new HashMap<>();
        map.put(AttrConstants.MENU, menu);
        map.put(AttrConstants.CITIES, CityNames.values());
        map.put(AttrConstants.CITY_INFOS, cityService.getDataAboutCity(menu));
        return map;
    }


}
