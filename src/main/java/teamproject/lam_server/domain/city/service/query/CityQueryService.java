package teamproject.lam_server.domain.city.service.query;

import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.dto.response.api.CityFoodAndViewResponse;
import teamproject.lam_server.domain.city.dto.response.api.CityGridDataResponse;
import teamproject.lam_server.domain.city.dto.response.api.TotalCityInfoResponse;

import java.util.List;

public interface CityQueryService {

    List<CityGridDataResponse> searchCurrentCityInfo();

    TotalCityInfoResponse searchTotalCityInfo(CityName cityName);

    CityFoodAndViewResponse searchCityFoodAndView(CityName cityName);
}
