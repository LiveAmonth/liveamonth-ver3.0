package teamproject.lam_server.domain.city.service.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import teamproject.lam_server.domain.city.dto.request.CreateCityIntroRequest;
import teamproject.lam_server.domain.city.entity.CityIntro;
import teamproject.lam_server.domain.city.repository.core.CityIntroRepository;
import teamproject.lam_server.domain.city.repository.core.CityTransportRepository;
import teamproject.lam_server.domain.city.repository.core.CityWeatherRepository;
import teamproject.lam_server.domain.city.repository.query.CityQueryRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static teamproject.lam_server.domain.city.constants.CityIntroCategory.INTRO;
import static teamproject.lam_server.domain.city.constants.CityName.SE;

@ExtendWith(MockitoExtension.class)
class CityCoreServiceTest {
    @InjectMocks
    CityAdminServiceImpl cityCoreService;

    @Mock
    CityIntroRepository cityIntroRepository;
    @Mock CityTransportRepository cityTransportRepository;
    @Mock CityWeatherRepository cityWeatherRepository;
    @Mock CityQueryRepository cityQueryRepository;


    @Test
    public void 도시_저장() throws Exception{
        //given
        CreateCityIntroRequest request = createCityInfo();
        CityIntro cityIntro = createCityInfoEntity(request);

        Long fakeCityInfoId = 1l;
        ReflectionTestUtils.setField(cityIntro, "id", fakeCityInfoId);

        //mocking
        given(cityIntroRepository.save(any()))
                .willReturn(cityIntro);
        given(cityIntroRepository.findById(fakeCityInfoId))
                .willReturn(Optional.ofNullable(cityIntro));

        //when
        Long newCityInfoId = cityCoreService.saveIntro(request).getId();

        //then
        CityIntro findCityIntro = cityIntroRepository.findById(newCityInfoId).get();

        assertEquals(cityIntro.getId(), findCityIntro.getId());
        assertEquals(cityIntro.getName(), findCityIntro.getName());
        assertEquals(cityIntro.getCityInfoCat(), findCityIntro.getCityInfoCat());
    }
    private CityIntro createCityInfoEntity(CreateCityIntroRequest request){
        return request.toEntity();
    }
    private CreateCityIntroRequest createCityInfo(){
        CreateCityIntroRequest request = new CreateCityIntroRequest();
        request.setName(SE);
        request.setCategory(INTRO);
        request.setContent("테스트 데이터");
        request.setImage("test.jpg");
        return request;
    }
}
