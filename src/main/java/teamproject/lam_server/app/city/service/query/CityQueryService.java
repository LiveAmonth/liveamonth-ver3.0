package teamproject.lam_server.app.city.service.query;

import teamproject.lam_server.app.city.dto.CityFoodAndViewResponse;
import teamproject.lam_server.app.city.dto.CityGridDataResponse;
import teamproject.lam_server.app.city.dto.CitySlideResponse;
import teamproject.lam_server.app.city.dto.TotalCityInfoResponse;
import teamproject.lam_server.constants.CategoryConstants.CityName;

import java.util.List;

public interface CityQueryService {

    List<CitySlideResponse> searchCityImage();

    List<CityGridDataResponse> searchCurrentCityInfo();

    TotalCityInfoResponse searchTotalCityInfo(CityName cityName);

    CityFoodAndViewResponse searchCityFoodAndView(CityName cityName);
}
