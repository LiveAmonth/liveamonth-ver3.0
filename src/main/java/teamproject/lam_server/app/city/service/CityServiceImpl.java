package teamproject.lam_server.app.city.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.city.repository.CityInfoRepository;
import teamproject.lam_server.app.city.repository.CityTransportRepository;
import teamproject.lam_server.app.city.repository.CityWeatherRepository;
import teamproject.lam_server.app.city.entity.CityInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static teamproject.lam_server.constants.AttrConstants.CITY_TRANSPORTS;
import static teamproject.lam_server.constants.AttrConstants.CITY_WEATHERS;
import static teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;
import static teamproject.lam_server.constants.CategoryConstants.CityNames;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityServiceImpl implements CityService{
    private final CityInfoRepository cityInfoRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityWeatherRepository cityWeatherRepository;

    public List<CityInfo> findCityInfoByCategory(CityInfoCategory category) {
        return cityInfoRepository.findCityInfosByCityInfoCategory(category);
    }

    public Map<String, Object> getDataAboutCity(CityNames menu) {
        Map<String, Object> cityInfoMap = new HashMap<>();
        for (CityInfoCategory category : CityInfoCategory.values()) {
            cityInfoMap.put(category.name(), cityInfoRepository.findCityInfosByCityInfoCategoryAndCity_Name(category,menu));
        }
        cityInfoMap.put(CITY_TRANSPORTS, cityTransportRepository.findCityTransportsByCity_Name(menu));
        cityInfoMap.put(CITY_WEATHERS, cityWeatherRepository.findCityWeathersByCity_Name(menu));
        return cityInfoMap;
    }
}
