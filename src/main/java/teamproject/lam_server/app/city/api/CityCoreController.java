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
import teamproject.lam_server.global.dto.Result;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/city")
public class CityCoreController {

    private final CityCoreService cityCoreService;

    @PostMapping("/infos/join")
    public ResponseEntity<Result> saveCityInfos(@RequestBody @Valid CreateCityInfoRequest request) {
        CreateEntityResponse createEntityResponse = cityCoreService.saveCityInfo(request);
        return ResponseEntity.ok().body(new Result(createEntityResponse));
    }

    @GetMapping("/infos")
    public ResponseEntity<Result> findCityInfos(Pageable pageable) {
        Page<CityInfoResponse> response = cityCoreService.findAllCityInfos(pageable);
        return ResponseEntity.ok().body(new Result(response));
    }

    @GetMapping("/transports")
    public ResponseEntity<Result> findCityTransports(Pageable pageable) {
        Page<CityTransportResponse> response = cityCoreService.findAllCityTransports(pageable);
        return ResponseEntity.ok().body(new Result(response));
    }

    @GetMapping("/weather")
    public ResponseEntity<Result> findCityWeather(Pageable pageable) {
        Page<CityWeatherResponse> response = cityCoreService.findAllCityWeathers(pageable);
        return ResponseEntity.ok().body(new Result(response));
    }

    @GetMapping("/infos/search")
    public ResponseEntity<Result> searchCityInfos(CityInfoSearchCond cond, Pageable pageable) {
        Page<CityInfoResponse> response = cityCoreService.searchCityInfos(cond, pageable);
        return ResponseEntity.ok().body(new Result(response));
    }

    @GetMapping("/transports/search")
    public ResponseEntity<Result> searchCityTransports(CityTransportSearchCond cond, Pageable pageable) {
        Page<CityTransportResponse> response = cityCoreService.searchCityTransports(cond, pageable);
        return ResponseEntity.ok().body(new Result(response));
    }

    @GetMapping("/weather/search")
    public ResponseEntity<Result> searchCityWeather(CityWeatherSearchCond cond, Pageable pageable) {
        Page<CityWeatherResponse> response = cityCoreService.searchCityWeathers(cond, pageable);
        return ResponseEntity.ok().body(new Result(response));
    }

}
