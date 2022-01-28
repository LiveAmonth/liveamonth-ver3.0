package teamproject.lam_server.app.city.service.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import teamproject.lam_server.app.city.dto.CreateCityInfoRequest;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.app.city.repository.core.CityInfoRepository;
import teamproject.lam_server.app.city.repository.core.CityTransportRepository;
import teamproject.lam_server.app.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.app.city.repository.query.CityQueryRepository;
import teamproject.lam_server.constants.CategoryConstants;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CityCoreServiceTest {
    @InjectMocks CityCoreServiceImpl cityCoreService;

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
        request.setName(CategoryConstants.CityName.SEOUL);
        request.setCategory(CategoryConstants.CityInfoCategory.INTRO);
        request.setContent("테스트 데이터");
        request.setImage("test.jpg");
        return request;
    }
}