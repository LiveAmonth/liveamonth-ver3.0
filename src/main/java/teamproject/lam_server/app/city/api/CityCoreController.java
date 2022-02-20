package teamproject.lam_server.app.city.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.app.city.dto.*;
import teamproject.lam_server.app.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.app.city.service.core.CityCoreService;
import teamproject.lam_server.global.dto.Response;
import teamproject.lam_server.global.dto.Result;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/city")
public class CityCoreController {
    private final Response response;

    private final CityCoreService cityCoreService;

    @PostMapping("/infos/join")
    public ResponseEntity<?> saveCityInfos(@RequestBody @Valid CreateCityInfoRequest request) {
        CreateEntityResponse result = cityCoreService.saveCityInfo(request);
        return response.success(result);
    }

    @GetMapping("/infos")
    public ResponseEntity<?> findCityInfos(Pageable pageable) {
        Page<CityInfoResponse> result = cityCoreService.findAllCityInfos(pageable);
        return response.success(result);
    }

    @GetMapping("/transports")
    public ResponseEntity<?> findCityTransports(Pageable pageable) {
        Page<CityTransportResponse> result = cityCoreService.findAllCityTransports(pageable);
        return response.success(result);
    }

    @GetMapping("/weather")
    public ResponseEntity<?> findCityWeather(Pageable pageable) {
        Page<CityWeatherResponse> result = cityCoreService.findAllCityWeathers(pageable);
        return response.success(result);
    }

    @GetMapping("/infos/search")
    public ResponseEntity<?> searchCityInfos(CityInfoSearchCond cond, Pageable pageable) {
        Page<CityInfoResponse> result = cityCoreService.searchCityInfos(cond, pageable);
        return response.success(result);
    }

    @GetMapping("/transports/search")
    public ResponseEntity<?> searchCityTransports(CityTransportSearchCond cond, Pageable pageable) {
        Page<CityTransportResponse> result = cityCoreService.searchCityTransports(cond, pageable);
        return response.success(result);
    }

    @GetMapping("/weather/search")
    public ResponseEntity<?> searchCityWeather(CityWeatherSearchCond cond, Pageable pageable) {
        Page<CityWeatherResponse> result = cityCoreService.searchCityWeathers(cond, pageable);
        return response.success(result);
    }

}
