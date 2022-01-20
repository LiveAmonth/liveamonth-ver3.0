package teamproject.lam_server.app.city.service.core;

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
    List<CityInfoResponse> findAllCityInfos();

    List<CityTransportResponse> findAllCityTransports();

    List<CityWeatherResponse> findAllCityWeathers();

    List<CityInfoResponse> searchCityInfos(CityInfoSearchCond cond);

    List<CityTransportResponse> searchCityTransports(CityTransportSearchCond cond);

    List<CityWeatherResponse> searchCityWeathers(CityWeatherSearchCond cond);

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

