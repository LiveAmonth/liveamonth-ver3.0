package teamproject.lam_server.app.city.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.city.dto.*;
import teamproject.lam_server.app.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.app.city.repository.core.CityInfoRepository;
import teamproject.lam_server.app.city.repository.core.CityTransportRepository;
import teamproject.lam_server.app.city.repository.query.CityQueryRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityCoreServiceImpl implements CityCoreService{

    private final CityInfoRepository cityInfoRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityQueryRepository cityQueryRepository;

    @Override
    public CreateEntityResponse saveCityInfo(CreateCityInfoRequest request) {
        return null;
    }

    @Override
    public List<CityInfoResponse> findAllCityInfos() {
        return null;
    }

    @Override
    public List<CityTransportResponse> findAllCityTransports() {
        return null;
    }

    @Override
    public List<CityWeatherResponse> findAllCityWeathers() {
        return null;
    }

    @Override
    public List<CityInfoResponse> searchCityInfos(CityInfoSearchCond cond) {
        return null;
    }

    @Override
    public List<CityTransportResponse> searchCityTransports(CityTransportSearchCond cond) {
        return null;
    }

    @Override
    public List<CityWeatherResponse> searchCityWeathers(CityWeatherSearchCond cond) {
        return null;
    }

    @Override
    public CreateEntityResponse updateCityInfo(UpdateCityInfoRequest request) {
        return null;
    }

    @Override
    public CreateEntityResponse updateCityTransport(UpdateCityTransportRequest request) {
        return null;
    }

    @Override
    public CreateEntityResponse updateCityWeather(UpdateCityWeatherRequest request) {
        return null;
    }

    @Override
    public CreateEntityResponse deleteCityInfo(Long id) {
        return null;
    }


}
