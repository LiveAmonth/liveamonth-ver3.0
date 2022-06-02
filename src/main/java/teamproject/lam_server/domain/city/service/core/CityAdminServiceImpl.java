package teamproject.lam_server.domain.city.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import teamproject.lam_server.domain.city.entity.CityInfo;
import teamproject.lam_server.domain.city.repository.core.CityInfoRepository;
import teamproject.lam_server.domain.city.repository.core.CityTransportRepository;
import teamproject.lam_server.domain.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.domain.city.repository.query.CityQueryRepository;
import teamproject.lam_server.domain.city.sort.CityInfoSortType;
import teamproject.lam_server.domain.city.sort.CityTransportSortType;
import teamproject.lam_server.domain.city.sort.CityWeatherSortType;
import teamproject.lam_server.global.dto.PostIdResponse;
import teamproject.lam_server.paging.DomainSpec;
import teamproject.lam_server.paging.PageableDTO;

import static teamproject.lam_server.global.exception.ErrorCode.CITY_NOT_FOUND;
import static teamproject.lam_server.util.BasicServiceUtil.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityAdminServiceImpl implements CityAdminService {

    private final CityInfoRepository cityInfoRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityWeatherRepository cityWeatherRepository;
    private final CityQueryRepository cityQueryRepository;
    private final DomainSpec<CityInfoSortType> infoSpec = new DomainSpec<>(CityInfoSortType.class);
    private final DomainSpec<CityTransportSortType> transportSpec = new DomainSpec<>(CityTransportSortType.class);
    private final DomainSpec<CityWeatherSortType> weatherSpec = new DomainSpec<>(CityWeatherSortType.class);

    @Override
    @Transactional
    public PostIdResponse saveCityInfo(CreateCityInfoRequest request) {
        CityInfo save = cityInfoRepository.save(request.toEntity());
        return PostIdResponse.of(save.getId());
    }


    @Override
    public Page<CityInfoResponse> searchInfo(CityInfoSearchCond cond, PageableDTO pageableDTO) {
        Pageable pageable = infoSpec.getPageable(pageableDTO);
        return cityQueryRepository
                .searchInfo(cond, pageable)
                .map(CityInfoResponse::of);
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
    public CityInfoResponse findInfoById(Long infoId) {
        return CityInfoResponse.of(
                cityInfoRepository.findById(infoId)
                .orElseThrow(getExceptionSupplier(CITY_NOT_FOUND))
        );
    }

    @Override
    public CityTransportResponse findTransportById(Long transportId) {
        return CityTransportResponse.of(
                cityTransportRepository.findById(transportId)
                        .orElseThrow(getExceptionSupplier(CITY_NOT_FOUND))
        );
    }

    @Override
    public CityWeatherResponse findWeatherById(Long weatherId) {
        return CityWeatherResponse.of(
                cityWeatherRepository.findById(weatherId)
                        .orElseThrow(getExceptionSupplier(CITY_NOT_FOUND))
        );
    }

    @Override
    public PostIdResponse updateCityInfo(UpdateCityInfoRequest request) {
        return null;
    }

    @Override
    public PostIdResponse updateCityTransport(UpdateCityTransportRequest request) {
        return null;
    }

    @Override
    public PostIdResponse updateCityWeather(UpdateCityWeatherRequest request) {
        return null;
    }

    @Override
    public PostIdResponse deleteCityInfo(Long id) {
        return null;
    }


}
