package teamproject.lam_server.app.city.service.core;

import teamproject.lam_server.app.city.dto.CityInfoResponse;
import teamproject.lam_server.app.city.dto.CityTransportResponse;
import teamproject.lam_server.app.city.dto.CityWeatherResponse;
import teamproject.lam_server.app.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityWeatherSearchCond;

import java.util.List;

public interface CityCoreService {

    //==조회 로직==//
    List<CityInfoResponse> findAllCityInfos();
    List<CityTransportResponse> findAllCityTransports();
    List<CityWeatherResponse> findAllCityWeathers();

    List<CityInfoResponse> findCityInfos(CityInfoSearchCond cond);
    List<CityTransportResponse> findCityTransports(CityTransportSearchCond cond);
    List<CityWeatherResponse> findCityWeathers(CityWeatherSearchCond cond);


}

