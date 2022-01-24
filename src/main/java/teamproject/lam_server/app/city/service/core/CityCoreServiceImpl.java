package teamproject.lam_server.app.city.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.city.dto.*;
import teamproject.lam_server.app.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.app.city.repository.core.CityInfoRepository;
import teamproject.lam_server.app.city.repository.core.CityTransportRepository;
import teamproject.lam_server.app.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.app.city.repository.query.CityQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityCoreServiceImpl implements CityCoreService {

    private final CityInfoRepository cityInfoRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityWeatherRepository cityWeatherRepository;
    private final CityQueryRepository cityQueryRepository;

    @Override
    @Transactional
    public CreateEntityResponse saveCityInfo(CreateCityInfoRequest request) {
        // valid 로직 추가하기
        CityInfo save = cityInfoRepository.save(request.toEntity());
        return new CreateEntityResponse(save.getId());
    }

    @Override
    public Page<CityInfoResponse> findAllCityInfos(Pageable pageable) {
        return cityInfoRepository
                .findAll(pageable)
                .map(CityInfoResponse::new);
    }

    @Override
    public Page<CityTransportResponse> findAllCityTransports(Pageable pageable) {
        return cityTransportRepository
                .findAll(pageable)
                .map(CityTransportResponse::new);
    }

    @Override
    public Page<CityWeatherResponse> findAllCityWeathers(Pageable pageable) {
        return cityWeatherRepository
                .findAll(pageable)
                .map(CityWeatherResponse::new);
    }

    @Override
    public Page<CityInfoResponse> searchCityInfos(CityInfoSearchCond cond, Pageable pageable) {
        return cityQueryRepository
                .searchCityInfos(cond, pageable)
                .map(CityInfoResponse::new);
    }

    @Override
    public Page<CityTransportResponse> searchCityTransports(CityTransportSearchCond cond, Pageable pageable) {
        return cityQueryRepository
                .searchCityTransports(cond, pageable)
                .map(CityTransportResponse::new);
    }

    @Override
    public Page<CityWeatherResponse> searchCityWeathers(CityWeatherSearchCond cond, Pageable pageable) {
        return cityQueryRepository
                .searchCityWeather(cond, pageable)
                .map(CityWeatherResponse::new);
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
