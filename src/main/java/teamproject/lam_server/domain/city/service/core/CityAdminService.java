package teamproject.lam_server.domain.city.service.core;

import org.springframework.data.domain.Page;
import teamproject.lam_server.domain.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.domain.city.dto.request.CreateCityInfoRequest;
import teamproject.lam_server.domain.city.dto.request.UpdateCityInfoRequest;
import teamproject.lam_server.domain.city.dto.request.UpdateCityTransportRequest;
import teamproject.lam_server.domain.city.dto.request.UpdateCityWeatherRequest;
import teamproject.lam_server.domain.city.dto.response.CityInfoResponse;
import teamproject.lam_server.domain.city.dto.response.CityTransportResponse;
import teamproject.lam_server.domain.city.dto.response.CityWeatherResponse;
import teamproject.lam_server.global.dto.PostIdResponse;
import teamproject.lam_server.paging.PageableDTO;

public interface CityAdminService {

    /**
     * CREATE
     */
    PostIdResponse saveCityInfo(CreateCityInfoRequest request);

    /**
     * READ
     */
    Page<CityInfoResponse> searchInfo(CityInfoSearchCond cond, PageableDTO pageableDTO);

    Page<CityTransportResponse> searchTransport(CityTransportSearchCond cond, PageableDTO pageableDTO);

    Page<CityWeatherResponse> searchWeathers(CityWeatherSearchCond cond, PageableDTO pageableDTO);

    CityInfoResponse findInfoById(Long InfoId);
    CityTransportResponse findTransportById(Long transportId);
    CityWeatherResponse findWeatherById(Long weatherId);

    /**
     * UPDATE
     */
    PostIdResponse updateCityInfo(UpdateCityInfoRequest request);

    PostIdResponse updateCityTransport(UpdateCityTransportRequest request);

    PostIdResponse updateCityWeather(UpdateCityWeatherRequest request);

    /**
     * DELETE
     */
    PostIdResponse deleteCityInfo(Long id);

}

