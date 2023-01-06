package com.lam.liveamonthapp.domain.city.service.core;

import org.springframework.data.domain.Page;
import com.lam.liveamonthapp.domain.city.dto.condition.CityIntroSearchCond;
import com.lam.liveamonthapp.domain.city.dto.condition.CityTransportSearchCond;
import com.lam.liveamonthapp.domain.city.dto.condition.CityWeatherSearchCond;
import com.lam.liveamonthapp.domain.city.dto.request.*;
import com.lam.liveamonthapp.domain.city.dto.response.CityIntroResponse;
import com.lam.liveamonthapp.domain.city.dto.response.CityTransportResponse;
import com.lam.liveamonthapp.domain.city.dto.response.CityWeatherResponse;
import com.lam.liveamonthapp.domain.city.dto.response.SimpleCityInfoResponse;
import com.lam.liveamonthapp.global.dto.request.IdListRequest;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.paging.PageableDTO;

public interface CityAdminService {

    /**
     * CREATE
     */
    PostIdResponse saveIntro(CityIntroCreate request);

    PostIdResponse saveTransport(CityTransportCreate request);

    PostIdResponse saveWeather(CityWeatherCreate request);

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
    void updateIntro(Long id, CityIntroEdit request);

    void updateTransport(Long id, CityTransportEdit request);


    /**
     * DELETE
     */
    void deleteIntro(IdListRequest request);

    void deleteTransport(IdListRequest request);

    void deleteWeather(IdListRequest request);

}

