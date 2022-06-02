package teamproject.lam_server.domain.city.service.query;

import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.dto.response.CityFoodAndViewResponse;
import teamproject.lam_server.domain.city.dto.response.CityGridDataResponse;
import teamproject.lam_server.domain.city.dto.response.CitySlideResponse;
import teamproject.lam_server.domain.city.dto.response.TotalCityInfoResponse;

import java.util.List;

public interface CityQueryService {

    List<CitySlideResponse> searchCityImage();

    List<CityGridDataResponse> searchCurrentCityInfo();

    TotalCityInfoResponse searchTotalCityInfo(CityName cityName);

    CityFoodAndViewResponse searchCityFoodAndView(CityName cityName);
}
