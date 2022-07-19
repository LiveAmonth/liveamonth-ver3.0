package teamproject.lam_server.domain.city.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.city.constants.CityIntroCategory;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;
import teamproject.lam_server.domain.city.dto.response.api.CityFoodAndViewResponse;
import teamproject.lam_server.domain.city.dto.response.api.CityGridDataResponse;
import teamproject.lam_server.domain.city.dto.response.api.ImageContentResponse;
import teamproject.lam_server.domain.city.dto.response.api.ExtraCityResponse;
import teamproject.lam_server.domain.city.entity.CityIntro;
import teamproject.lam_server.domain.city.repository.core.CityIntroRepository;
import teamproject.lam_server.domain.city.repository.core.CityTransportRepository;
import teamproject.lam_server.domain.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.domain.city.repository.query.CityQueryRepository;

import java.time.LocalDateTime;
import java.util.*;

import static teamproject.lam_server.domain.city.constants.CityIntroCategory.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityApiServiceImpl implements CityApiService {
    private final CityIntroRepository cityIntroRepository;
    private final CityWeatherRepository cityWeatherRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityQueryRepository cityQueryRepository;

    @Override
    public List<CityGridDataResponse> searchCurrentCityInfo() {
        return cityQueryRepository.findCityGridInfo(INTRO, getCurrentMonth());
    }

    private MonthCategory getCurrentMonth() {
        return MonthCategory.values()[LocalDateTime.now().getMonth().getValue() - 1];
    }

    @Override
    public ExtraCityResponse searchTotalCityInfo(CityName cityName) {
        return ExtraCityResponse.of(
                cityTransportRepository.findByName(cityName),
                cityWeatherRepository.findByName(cityName));
    }

    @Override
    public CityFoodAndViewResponse searchCityFoodAndView(CityName cityName) {
        return CityFoodAndViewResponse.of(
                cityIntroRepository.findByNameAndCityInfoCat(cityName, FOOD),
                cityIntroRepository.findByNameAndCityInfoCat(cityName, VIEW)
        );
    }

    @Override
    public Map<CityIntroCategory, List<ImageContentResponse>> getCity(CityName cityName) {
        List<CityIntro> cityIntros = cityIntroRepository.findByName(cityName);
        Map<CityIntroCategory, List<ImageContentResponse>> introMap = new HashMap<>();
        for (CityIntro cityIntro : cityIntros) {
            CityIntroCategory category = cityIntro.getCityInfoCat();
            if (introMap.containsKey(cityIntro.getCityInfoCat())){
                introMap.get(category).add(ImageContentResponse.of(cityIntro));
            }else {
                introMap.put(category, new ArrayList<>(Collections.singletonList(ImageContentResponse.of(cityIntro))));
            }

        }
        return introMap;
    }

}
