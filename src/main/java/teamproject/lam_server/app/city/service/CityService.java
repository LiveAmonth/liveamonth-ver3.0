package teamproject.lam_server.app.city.service;

import teamproject.lam_server.app.city.entity.City;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.app.city.entity.CityInfo;

import java.util.List;
import java.util.Map;

public interface CityService {

    List<CityInfo> findCityInfoByCategory(CategoryConstants.CityInfoCategory category);

    Map<String, Object> getDataAboutCity(CategoryConstants.CityNames menu);

    Long saveCityInfo(City city);

    City getCityInfoById(Long id);
}
