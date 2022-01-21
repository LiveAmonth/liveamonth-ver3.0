package teamproject.lam_server.app.city.service.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import teamproject.lam_server.app.city.dto.*;
import teamproject.lam_server.app.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityWeatherSearchCond;

import java.util.List;

public interface CityCoreService {

    /**
     * CREATE
     */
    CreateEntityResponse saveCityInfo(CreateCityInfoRequest request);

    /**
     * READ
     */
    Page<CityInfoResponse> findAllCityInfos(Pageable pageable);

    Page<CityTransportResponse> findAllCityTransports(Pageable pageable);

    Page<CityWeatherResponse> findAllCityWeathers(Pageable pageable);

    Page<CityInfoResponse> searchCityInfos(CityInfoSearchCond cond, Pageable pageable);

    Page<CityTransportResponse> searchCityTransports(CityTransportSearchCond cond, Pageable pageable);

    Page<CityWeatherResponse> searchCityWeathers(CityWeatherSearchCond cond, Pageable pageable);

    /**
     * UPDATE
     */
    CreateEntityResponse updateCityInfo(UpdateCityInfoRequest request);

    CreateEntityResponse updateCityTransport(UpdateCityTransportRequest request);

    CreateEntityResponse updateCityWeather(UpdateCityWeatherRequest request);

    /**
     * DELETE
     */
    CreateEntityResponse deleteCityInfo(Long id);

}

