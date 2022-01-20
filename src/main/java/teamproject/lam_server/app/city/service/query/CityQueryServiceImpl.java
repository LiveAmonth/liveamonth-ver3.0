package teamproject.lam_server.app.city.service.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.city.dto.view.CityFoodAndViewResponse;
import teamproject.lam_server.app.city.dto.view.CityGridDataResponse;
import teamproject.lam_server.app.city.dto.view.CitySlideResponse;
import teamproject.lam_server.app.city.dto.view.TotalCityInfoResponse;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.app.city.repository.core.CityTransportRepository;
import teamproject.lam_server.app.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.app.city.repository.query.CityQueryRepository;
import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.constants.CategoryConstants.Month;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;
import static teamproject.lam_server.constants.CategoryConstants.CityInfoCategory.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityQueryServiceImpl implements CityQueryService {
    private final CityWeatherRepository cityWeatherRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityQueryRepository cityQueryRepository;

    @Override
    public List<CitySlideResponse> searchCityImage() {
        List<CityInfo> cityIntroImage = cityQueryRepository.findCityInfo(null,INTRO);
        Collections.shuffle(cityIntroImage);
        return cityIntroImage.stream()
                .map(CitySlideResponse::new)
                .limit(3)
                .collect(toList());
    }

    @Override
    public List<CityGridDataResponse> searchCurrentCityInfo() {
        return cityQueryRepository.findCityGridInfo(INTRO, getCurrentMonth());

    }

    private Month getCurrentMonth() {
        return Month.values()[LocalDateTime.now().getMonth().getValue() - 1];
    }

    @Override
    public TotalCityInfoResponse searchTotalCityInfo(CityName cityName) {
        return new TotalCityInfoResponse(
                cityQueryRepository.findOneCityInfo(cityName, INTRO).orElseThrow(NoSuchElementException::new),
                cityTransportRepository.findByName(cityName),
                cityWeatherRepository.findByName(cityName));
    }

    @Override
    public CityFoodAndViewResponse searchCityFoodAndView(CityName cityName){
        return new CityFoodAndViewResponse(
                cityQueryRepository.findCityInfo(cityName, FOOD),
                cityQueryRepository.findCityInfo(cityName, VIEW)
        );
    }

}
