package teamproject.lam_server.domain.city.service.query;

import teamproject.lam_server.domain.city.constants.CityIntroCategory;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.dto.response.api.CityFoodAndViewResponse;
import teamproject.lam_server.domain.city.dto.response.api.CityGridDataResponse;
import teamproject.lam_server.domain.city.dto.response.api.ImageContentResponse;
import teamproject.lam_server.domain.city.dto.response.api.ExtraCityResponse;

import java.util.List;
import java.util.Map;

public interface CityApiService {

    List<CityGridDataResponse> searchCurrentCityInfo();

    ExtraCityResponse searchTotalCityInfo(CityName cityName);

    CityFoodAndViewResponse searchCityFoodAndView(CityName cityName);

    Map<CityIntroCategory, List<ImageContentResponse>> getCity(CityName cityName);
}
