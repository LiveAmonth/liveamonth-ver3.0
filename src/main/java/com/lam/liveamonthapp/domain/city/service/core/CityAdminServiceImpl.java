package com.lam.liveamonthapp.domain.city.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.city.constants.sort.CityIntroSortType;
import com.lam.liveamonthapp.domain.city.constants.sort.CityTransportSortType;
import com.lam.liveamonthapp.domain.city.constants.sort.CityWeatherSortType;
import com.lam.liveamonthapp.domain.city.dto.condition.CityIntroSearchCond;
import com.lam.liveamonthapp.domain.city.dto.condition.CityTransportSearchCond;
import com.lam.liveamonthapp.domain.city.dto.condition.CityWeatherSearchCond;
import com.lam.liveamonthapp.domain.city.dto.request.*;
import com.lam.liveamonthapp.domain.city.dto.response.CityIntroResponse;
import com.lam.liveamonthapp.domain.city.dto.response.CityTransportResponse;
import com.lam.liveamonthapp.domain.city.dto.response.CityWeatherResponse;
import com.lam.liveamonthapp.domain.city.dto.response.SimpleCityInfoResponse;
import com.lam.liveamonthapp.domain.city.entity.CityIntro;
import com.lam.liveamonthapp.domain.city.entity.CityTransport;
import com.lam.liveamonthapp.domain.city.entity.CityWeather;
import com.lam.liveamonthapp.domain.city.repository.core.CityIntroRepository;
import com.lam.liveamonthapp.domain.city.repository.core.CityTransportRepository;
import com.lam.liveamonthapp.domain.city.repository.core.CityWeatherRepository;
import com.lam.liveamonthapp.domain.city.repository.query.CityQueryRepository;
import com.lam.liveamonthapp.exception.notfound.CityNotFound;
import com.lam.liveamonthapp.global.dto.request.IdListRequest;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;
import com.lam.liveamonthapp.paging.DomainSpec;
import com.lam.liveamonthapp.paging.PageableDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityAdminServiceImpl implements CityAdminService {

    private final CityIntroRepository cityIntroRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityWeatherRepository cityWeatherRepository;
    private final CityQueryRepository cityQueryRepository;
    private final DomainSpec<CityIntroSortType> introSpec = new DomainSpec<>(CityIntroSortType.class);
    private final DomainSpec<CityTransportSortType> transportSpec = new DomainSpec<>(CityTransportSortType.class);
    private final DomainSpec<CityWeatherSortType> weatherSpec = new DomainSpec<>(CityWeatherSortType.class);

    @Override
    public PostIdResponse saveIntro(CityIntroCreate request) {
        CityIntro save = cityIntroRepository.save(request.toEntity());
        return PostIdResponse.of(save.getId());
    }

    @Override
    public PostIdResponse saveTransport(CityTransportCreate request) {
        CityTransport save = cityTransportRepository.save(request.toEntity());
        return PostIdResponse.of(save.getId());
    }

    @Override
    public PostIdResponse saveWeather(CityWeatherCreate request) {
        CityWeather save = cityWeatherRepository.save(request.toEntity());
        return PostIdResponse.of(save.getId());
    }

    @Override
    public Page<SimpleCityInfoResponse> searchIntro(CityIntroSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = introSpec.getPageable(pageableDTO);
        return cityQueryRepository
                .searchIntro(cond, pageable)
                .map(SimpleCityInfoResponse::of);
    }

    @Override
    public Page<CityTransportResponse> searchTransport(CityTransportSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = transportSpec.getPageable(pageableDTO);
        return cityQueryRepository
                .searchTransport(cond, pageable)
                .map(CityTransportResponse::of);
    }

    @Override
    public Page<CityWeatherResponse> searchWeathers(CityWeatherSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = weatherSpec.getPageable(pageableDTO);
        return cityQueryRepository
                .searchWeather(cond, pageable)
                .map(CityWeatherResponse::of);
    }

    @Override
    public CityIntroResponse findIntroById(Long infoId) {
        return CityIntroResponse.of(
                cityIntroRepository.findById(infoId)
                        .orElseThrow(CityNotFound::new)
        );
    }

    @Override
    @Transactional
    public void updateIntro(Long id, CityIntroEdit request) {
        CityIntro cityIntro = cityIntroRepository.getById(id);
        cityIntro.updateContent(request.getContent());
    }

    @Override
    @Transactional
    public void updateTransport(Long id, CityTransportEdit request) {
        CityTransport cityTransport = cityTransportRepository.getById(id);
        cityTransport.updateStationCount(request.getStationCount());
    }

    @Override
    public void deleteIntro(IdListRequest request) {
        cityIntroRepository.deleteAllByIdInQuery(request.getIds());
    }

    @Override
    public void deleteTransport(IdListRequest request) {
        cityTransportRepository.deleteAllByIdInQuery(request.getIds());
    }

    @Override
    public void deleteWeather(IdListRequest request) {
        cityWeatherRepository.deleteAllByIdInQuery(request.getIds());
    }

}
