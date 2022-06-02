package teamproject.lam_server.domain.city.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.domain.city.dto.request.CreateCityInfoRequest;
import teamproject.lam_server.domain.city.dto.response.CityInfoResponse;
import teamproject.lam_server.domain.city.dto.response.CityTransportResponse;
import teamproject.lam_server.domain.city.dto.response.CityWeatherResponse;
import teamproject.lam_server.domain.city.service.core.CityAdminService;
import teamproject.lam_server.global.dto.CustomResponse;
import teamproject.lam_server.global.dto.PostIdResponse;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/city")
public class CityAdminController {
    private final CityAdminService cityService;

    @PostMapping("/info")
    public ResponseEntity<?> saveCityInfos(@RequestBody @Valid CreateCityInfoRequest request) {
        PostIdResponse result = cityService.saveCityInfo(request);
        return CustomResponse.success(result);
    }

    @GetMapping("/info/search")
    public ResponseEntity<?> searchCityInfos(CityInfoSearchCond cond, PageableDTO pageableDTO) {
        Page<CityInfoResponse> result = cityService.searchInfo(cond, pageableDTO);
        return CustomResponse.success(result);
    }

    @GetMapping("/transport/search")
    public ResponseEntity<?> searchCityTransports(CityTransportSearchCond cond,  PageableDTO pageableDTO) {
        Page<CityTransportResponse> result = cityService.searchTransport(cond, pageableDTO);
        return CustomResponse.success(result);
    }

    @GetMapping("/weather/search")
    public ResponseEntity<?> searchCityWeather(CityWeatherSearchCond cond,  PageableDTO pageableDTO) {
        Page<CityWeatherResponse> result = cityService.searchWeathers(cond, pageableDTO);
        return CustomResponse.success(result);
    }

}
