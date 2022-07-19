package teamproject.lam_server.domain.city.service.core;

import org.springframework.data.domain.Page;
import teamproject.lam_server.domain.city.dto.condition.CityIntroSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.domain.city.dto.request.*;
import teamproject.lam_server.domain.city.dto.response.CityIntroResponse;
import teamproject.lam_server.domain.city.dto.response.CityTransportResponse;
import teamproject.lam_server.domain.city.dto.response.CityWeatherResponse;
import teamproject.lam_server.domain.city.dto.response.SimpleCityInfoResponse;
import teamproject.lam_server.global.dto.IdListRequest;
import teamproject.lam_server.global.dto.PostIdResponse;
import teamproject.lam_server.paging.PageableDTO;

public interface CityAdminService {

    /**
     * CREATE
     */
    PostIdResponse saveIntro(CreateCityIntroRequest request);

    PostIdResponse saveTransport(CreateCityTransportRequest request);

    PostIdResponse saveWeather(CreateCityWeatherRequest request);

    /**
     * READ
     */
    Page<SimpleCityInfoResponse> searchIntro(CityIntroSearchCond cond, PageableDTO pageableDTO);

    Page<CityTransportResponse> searchTransport(CityTransportSearchCond cond, PageableDTO pageableDTO);

    Page<CityWeatherResponse> searchWeathers(CityWeatherSearchCond cond, PageableDTO pageableDTO);

    CityIntroResponse findIntroById(Long InfoId);


    /**
     * UPDATE
     */
    void updateIntro(Long id, UpdateCityIntroRequest request);

    void updateTransport(Long id, UpdateCityTransportRequest request);


    /**
     * DELETE
     */
    void deleteIntro(IdListRequest request);

    void deleteTransport(IdListRequest request);

    void deleteWeather(IdListRequest request);

}

