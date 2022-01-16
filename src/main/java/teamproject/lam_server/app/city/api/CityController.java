package teamproject.lam_server.app.city.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.app.city.dto.CreateCityInfoRequest;
import teamproject.lam_server.app.city.dto.CreateCityInfoResponse;
import teamproject.lam_server.app.city.repository.CityRepository;
import teamproject.lam_server.app.city.service.CityService;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityNames;
import teamproject.lam_server.controller.MainController;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.global.dto.Result;

import javax.validation.Valid;
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

    @PostMapping("/city-infos/new")
    public ResponseEntity<Result> createCityInfo(@RequestBody @Valid CreateCityInfoRequest request){
        CityInfo cityInfo = request.toEntity();
        Long id = cityService.saveCityInfo(cityInfo);
        return null;

    }
}
