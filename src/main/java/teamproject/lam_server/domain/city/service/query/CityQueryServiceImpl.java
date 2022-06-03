package teamproject.lam_server.domain.city.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;
import teamproject.lam_server.domain.city.dto.response.api.CityFoodAndViewResponse;
import teamproject.lam_server.domain.city.dto.response.api.CityGridDataResponse;
import teamproject.lam_server.domain.city.dto.response.api.TotalCityInfoResponse;
import teamproject.lam_server.domain.city.repository.core.CityIntroRepository;
import teamproject.lam_server.domain.city.repository.core.CityTransportRepository;
import teamproject.lam_server.domain.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.domain.city.repository.query.CityQueryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static teamproject.lam_server.domain.city.constants.CityIntroCategory.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityQueryServiceImpl implements CityQueryService {
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
    public TotalCityInfoResponse searchTotalCityInfo(CityName cityName) {
        return TotalCityInfoResponse.of(
                cityIntroRepository.findOneByNameAndCityInfoCat(cityName, INTRO).orElseThrow(NoSuchElementException::new),
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

}
