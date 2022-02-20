package teamproject.lam_server.app.city.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import teamproject.lam_server.app.city.dto.view.CityFoodAndViewResponse;
import teamproject.lam_server.app.city.dto.view.CityGridDataResponse;
import teamproject.lam_server.app.city.dto.view.TotalCityInfoResponse;
import teamproject.lam_server.app.city.service.query.CityQueryService;
import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.global.dto.MenuResponse;
import teamproject.lam_server.global.dto.Response;
import teamproject.lam_server.global.dto.Result;
import teamproject.lam_server.global.service.MenuService;

import java.util.List;

@Api(tags = {"City"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/city")
public class CityApiController {
    private final CityQueryService cityQueryService;
    private final MenuService menuService;
    private final Response response;
    /**
     * dependence presentation layer::home(header)
     * -> menu bar
     */
    @ApiOperation(value = "도시 이름 조회",notes = "등록되어 있는 도시 이름을 가져온다.")
    @GetMapping("/names")
    public ResponseEntity<?> getCityNames() {
        MenuResponse result = menuService.getCityMenus();
        return response.success(result);
    }

    /**
     * dependence presentation layer::home(body)
     * -> slide info(top)
     */
    @GetMapping("/slide-infos")
    public ResponseEntity<?> searchCityGridInfos() {
        List<CityGridDataResponse> result = cityQueryService.searchCurrentCityInfo();
        return response.success(result);
    }

    /**
     * dependence presentation layer::city(body)
     * -> total city info tab pane(top)
     */
    @GetMapping("{cityName}/total-infos")
    public ResponseEntity<?> getTotalCityInfo(@PathVariable("cityName") CityName cityName) {
        TotalCityInfoResponse result = cityQueryService.searchTotalCityInfo(cityName);
        return response.success(result);
    }

    /**
     * dependence presentation layer::city(body)
     * -> food & view image slide(bottom)
     */
    @GetMapping("{cityName}/foods-and-view")
    public ResponseEntity<?> getCityFoodAndViewInfo(@PathVariable("cityName") CityName cityName) {
        CityFoodAndViewResponse result = cityQueryService.searchCityFoodAndView(cityName);
        return response.success(result);
    }


}
