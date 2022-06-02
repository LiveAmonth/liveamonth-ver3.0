package teamproject.lam_server.domain.city.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;
import teamproject.lam_server.domain.city.dto.response.CityFoodAndViewResponse;
import teamproject.lam_server.domain.city.dto.response.CityGridDataResponse;
import teamproject.lam_server.domain.city.dto.response.CitySlideResponse;
import teamproject.lam_server.domain.city.dto.response.TotalCityInfoResponse;
import teamproject.lam_server.domain.city.entity.CityInfo;
import teamproject.lam_server.domain.city.repository.core.CityInfoRepository;
import teamproject.lam_server.domain.city.repository.core.CityTransportRepository;
import teamproject.lam_server.domain.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.domain.city.repository.query.CityQueryRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;
import static teamproject.lam_server.domain.city.constants.CityInfoCategory.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityQueryServiceImpl implements CityQueryService {
    private final CityInfoRepository cityInfoRepository;
    private final CityWeatherRepository cityWeatherRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityQueryRepository cityQueryRepository;

    @Override
    public List<CitySlideResponse> searchCityImage() {
        List<CityInfo> cityIntroImage = cityInfoRepository.findByCityInfoCat(INTRO);
        Collections.shuffle(cityIntroImage);
        return cityIntroImage.stream()
                .map(CitySlideResponse::of)
                .limit(3)
                .collect(toList());
    }

    @Override
    public List<CityGridDataResponse> searchCurrentCityInfo() {
        return cityQueryRepository.findCityGridInfo(INTRO, getCurrentMonth());

    }

    private MonthCategory getCurrentMonth() {
        return MonthCategory.values()[LocalDateTime.now().getMonth().getValue() - 1];
    }

    @Override
    public TotalCityInfoResponse searchTotalCityInfo(CityName cityName) {
        return TotalCityInfoResponse.of(
                cityInfoRepository.findOneByNameAndCityInfoCat(cityName, INTRO).orElseThrow(NoSuchElementException::new),
                cityTransportRepository.findByName(cityName),
                cityWeatherRepository.findByName(cityName));
    }

    @Override
    public CityFoodAndViewResponse searchCityFoodAndView(CityName cityName) {
        return CityFoodAndViewResponse.of(
                cityInfoRepository.findByNameAndCityInfoCat(cityName, FOOD),
                cityInfoRepository.findByNameAndCityInfoCat(cityName, VIEW)
        );
    }

}
