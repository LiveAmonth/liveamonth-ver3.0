package teamproject.lam_server.app.city.service.query;

import teamproject.lam_server.app.city.dto.view.CityFoodAndViewResponse;
import teamproject.lam_server.app.city.dto.view.CityGridDataResponse;
import teamproject.lam_server.app.city.dto.view.CitySlideResponse;
import teamproject.lam_server.app.city.dto.view.TotalCityInfoResponse;
import teamproject.lam_server.constants.CategoryConstants.CityName;

import java.util.List;

public interface CityQueryService {

    List<CitySlideResponse> searchCityImage();

    List<CityGridDataResponse> searchCurrentCityInfo();

    TotalCityInfoResponse searchTotalCityInfo(CityName cityName);

    CityFoodAndViewResponse searchCityFoodAndView(CityName cityName);
}
