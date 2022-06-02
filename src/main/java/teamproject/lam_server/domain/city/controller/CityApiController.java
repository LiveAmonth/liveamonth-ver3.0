package teamproject.lam_server.domain.city.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.dto.response.CityFoodAndViewResponse;
import teamproject.lam_server.domain.city.dto.response.CityGridDataResponse;
import teamproject.lam_server.domain.city.dto.response.TotalCityInfoResponse;
import teamproject.lam_server.domain.city.service.query.CityQueryService;
import teamproject.lam_server.global.dto.CustomResponse;

import java.util.List;

@Api(tags = {"City"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/city")
public class CityApiController {
    private final CityQueryService cityQueryService;
    private final CustomResponse customResponse;

    /**
     * dependence presentation layer::home(body)
     * -> slide info(top)
     */
    @GetMapping("/slide-infos")
    public ResponseEntity<?> searchCityGridInfos() {
        List<CityGridDataResponse> result = cityQueryService.searchCurrentCityInfo();
        return customResponse.success(result);
    }

    /**
     * dependence presentation layer::city(body)
     * -> total city info tab pane(top)
     */
    @GetMapping("{cityName}/total-infos")
    public ResponseEntity<?> getTotalCityInfo(@PathVariable("cityName") CityName cityName) {
        TotalCityInfoResponse result = cityQueryService.searchTotalCityInfo(cityName);
        return customResponse.success(result);
    }

    /**
     * dependence presentation layer::city(body)
     * -> food & view image slide(bottom)
     */
    @GetMapping("{cityName}/foods-and-view")
    public ResponseEntity<?> getCityFoodAndViewInfo(@PathVariable("cityName") CityName cityName) {
        CityFoodAndViewResponse result = cityQueryService.searchCityFoodAndView(cityName);
        return customResponse.success(result);
    }


}
