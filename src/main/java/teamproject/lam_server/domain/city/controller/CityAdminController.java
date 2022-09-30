package teamproject.lam_server.domain.city.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.domain.city.dto.condition.CityIntroSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.domain.city.dto.request.*;
import teamproject.lam_server.domain.city.dto.response.CityIntroResponse;
import teamproject.lam_server.domain.city.dto.response.CityTransportResponse;
import teamproject.lam_server.domain.city.dto.response.CityWeatherResponse;
import teamproject.lam_server.domain.city.dto.response.SimpleCityInfoResponse;
import teamproject.lam_server.domain.city.service.core.CityAdminService;
import teamproject.lam_server.global.dto.response.CustomResponse;
import teamproject.lam_server.global.dto.request.IdListRequest;
import teamproject.lam_server.global.dto.response.PostIdResponse;
import teamproject.lam_server.paging.PageableDTO;

import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/v1/city")
public class CityAdminController {
    private final CityAdminService cityService;

    @PostMapping("/intro")
    public ResponseEntity<?> saveIntro(@RequestBody @Valid CityIntroCreate request) {
        PostIdResponse result = cityService.saveIntro(request);
        return CustomResponse.success(CREATE_CITY, result);
    }

    @PostMapping("/transport")
    public ResponseEntity<?> saveTransport(@RequestBody @Valid CityTransportCreate request) {
        PostIdResponse result = cityService.saveTransport(request);
        return CustomResponse.success(CREATE_CITY, result);
    }

    @PostMapping("/weather")
    public ResponseEntity<?> saveWeather(@RequestBody @Valid CityWeatherCreate request) {
        PostIdResponse result = cityService.saveWeather(request);
        return CustomResponse.success(CREATE_CITY, result);
    }

    @GetMapping("/intro/search")
    public ResponseEntity<?> searchIntro(CityIntroSearchCond cond, PageableDTO pageableDTO) {
        Page<SimpleCityInfoResponse> result = cityService.searchIntro(cond, pageableDTO);
        return CustomResponse.success(READ_CITY, result);
    }

    @GetMapping("/transport/search")
    public ResponseEntity<?> searchTransport(CityTransportSearchCond cond, PageableDTO pageableDTO) {
        Page<CityTransportResponse> result = cityService.searchTransport(cond, pageableDTO);
        return CustomResponse.success(READ_CITY, result);
    }

    @GetMapping("/weather/search")
    public ResponseEntity<?> searchWeather(CityWeatherSearchCond cond, PageableDTO pageableDTO) {
        Page<CityWeatherResponse> result = cityService.searchWeathers(cond, pageableDTO);
        return CustomResponse.success(READ_CITY, result);
    }

    @GetMapping("/intro/{id}")
    public ResponseEntity<?> getIntro(@PathVariable Long id) {
        CityIntroResponse result = cityService.findIntroById(id);
        return CustomResponse.success(READ_CITY, result);
    }

    @PutMapping("/intro/{id}")
    public ResponseEntity<?> updateIntro(@PathVariable Long id, @RequestBody @Valid CityIntroEdit request) {
        cityService.updateIntro(id, request);
        return CustomResponse.success(UPDATE_CITY);
    }

    @PutMapping("/transport/{id}")
    public ResponseEntity<?> updateTransport(@PathVariable Long id, @RequestBody @Valid CityTransportEdit request) {
        cityService.updateTransport(id, request);
        return CustomResponse.success(UPDATE_CITY);
    }

    @DeleteMapping("/intro/delete")
    public ResponseEntity<?> deleteIntro(@RequestBody IdListRequest request) {
        cityService.deleteIntro(request);
        return CustomResponse.success(DELETE_CITY);
    }

    @DeleteMapping("/transport/delete")
    public ResponseEntity<?> deleteTransport(@RequestBody IdListRequest request) {
        cityService.deleteTransport(request);
        return CustomResponse.success(DELETE_CITY);
    }

    @DeleteMapping("/weather/delete")
    public ResponseEntity<?> deleteWeather(@RequestBody IdListRequest request) {
        cityService.deleteWeather(request);
        return CustomResponse.success(DELETE_CITY);
    }
}
