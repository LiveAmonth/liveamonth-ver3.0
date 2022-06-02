package teamproject.lam_server.domain.city.service.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import teamproject.lam_server.domain.city.dto.request.CreateCityInfoRequest;
import teamproject.lam_server.domain.city.entity.CityInfo;
import teamproject.lam_server.domain.city.repository.core.CityInfoRepository;
import teamproject.lam_server.domain.city.repository.core.CityTransportRepository;
import teamproject.lam_server.domain.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.domain.city.repository.query.CityQueryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static teamproject.lam_server.domain.city.constants.CityInfoCategory.INTRO;
import static teamproject.lam_server.domain.city.constants.CityName.SE;

@ExtendWith(MockitoExtension.class)
class CityCoreServiceTest {
    @InjectMocks
    CityAdminServiceImpl cityCoreService;

    @Mock CityInfoRepository cityInfoRepository;
    @Mock CityTransportRepository cityTransportRepository;
    @Mock CityWeatherRepository cityWeatherRepository;
    @Mock CityQueryRepository cityQueryRepository;


    @Test
    public void 도시_저장() throws Exception{
        //given
        CreateCityInfoRequest request = createCityInfo();
        CityInfo cityInfo = createCityInfoEntity(request);

        Long fakeCityInfoId = 1l;
        ReflectionTestUtils.setField(cityInfo, "id", fakeCityInfoId);

        //mocking
        given(cityInfoRepository.save(any()))
                .willReturn(cityInfo);
        given(cityInfoRepository.findById(fakeCityInfoId))
                .willReturn(Optional.ofNullable(cityInfo));

        //when
        Long newCityInfoId = cityCoreService.saveCityInfo(request).getId();

        //then
        CityInfo findCityInfo = cityInfoRepository.findById(newCityInfoId).get();

        assertEquals(cityInfo.getId(), findCityInfo.getId());
        assertEquals(cityInfo.getName(), findCityInfo.getName());
        assertEquals(cityInfo.getCityInfoCat(), findCityInfo.getCityInfoCat());
    }
    private CityInfo createCityInfoEntity(CreateCityInfoRequest request){
        return request.toEntity();
    }
    private CreateCityInfoRequest createCityInfo(){
        CreateCityInfoRequest request = new CreateCityInfoRequest();
        request.setName(SE);
        request.setCategory(INTRO);
        request.setContent("테스트 데이터");
        request.setImage("test.jpg");
        return request;
    }
}
