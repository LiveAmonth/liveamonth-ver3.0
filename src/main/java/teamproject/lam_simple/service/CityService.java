package teamproject.lam_simple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_simple.domain.City;
import teamproject.lam_simple.domain.CityInfo;
import teamproject.lam_simple.repository.CityRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory;
import static teamproject.lam_simple.constants.CategoryConstants.CityNames;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public List<CityInfo> findCityInfoByCategory(CityInfoCategory category) {
        return cityRepository.findCityInfoByCategory(category);
    }

    public Map<String, Object> findCityInfoByName(CityNames menu) {
        City city = cityRepository.findAllCityInfoWithGraphByName(menu);
        List<CityInfo> cityInfos = city.getCityInfos();
        Map<String, Object> cityInfoMap = new HashMap<>();
        List<CityInfo> views = new ArrayList<>();
        List<CityInfo> foods = new ArrayList<>();
        for (CityInfo cityInfo : cityInfos) {
            if(cityInfo.getCityInfoCategory() == CityInfoCategory.INTRO){
                cityInfoMap.put(CityInfoCategory.INTRO.name(), cityInfo);
            }else if(cityInfo.getCityInfoCategory() == CityInfoCategory.VIEW){
                views.add(cityInfo);
            }else{
                foods.add(cityInfo);
            }
        }
        cityInfoMap.put(CityInfoCategory.VIEW.name(), views);
        cityInfoMap.put(CityInfoCategory.FOOD.name(), foods);
        cityInfoMap.put("cityTransports", city.getCityTransports());
        cityInfoMap.put("cityWeathers", city.getCityWeathers());
        return cityInfoMap;
    }
}
