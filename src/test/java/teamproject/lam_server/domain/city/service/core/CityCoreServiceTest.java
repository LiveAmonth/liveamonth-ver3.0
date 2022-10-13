package teamproject.lam_server.domain.city.service.core;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CityCoreServiceTest {
//    @InjectMocks
//    CityAdminServiceImpl cityCoreService;
//
//    @Mock
//    CityIntroRepository cityIntroRepository;
//    @Mock
//    CityTransportRepository cityTransportRepository;
//    @Mock
//    CityWeatherRepository cityWeatherRepository;
//    @Mock
//    CityQueryRepository cityQueryRepository;
//
//
//    @Test
//    public void 도시_저장() throws Exception {
//        //given
//        CityIntroCreate request = createCityInfo();
//        CityIntro cityIntro = createCityInfoEntity(request);
//
//        Long fakeCityInfoId = 1l;
//        ReflectionTestUtils.setField(cityIntro, "id", fakeCityInfoId);
//
//        //mocking
//        given(cityIntroRepository.save(any()))
//                .willReturn(cityIntro);
//        given(cityIntroRepository.findById(fakeCityInfoId))
//                .willReturn(Optional.ofNullable(cityIntro));
//
//        //when
//        Long newCityInfoId = cityCoreService.saveIntro(request).getId();
//
//        //then
//        CityIntro findCityIntro = cityIntroRepository.findById(newCityInfoId).get();
//
//        assertEquals(cityIntro.getId(), findCityIntro.getId());
//        assertEquals(cityIntro.getName(), findCityIntro.getName());
//        assertEquals(cityIntro.getCityInfoCat(), findCityIntro.getCityInfoCat());
//    }
//
//    private CityIntro createCityInfoEntity(CityIntroCreate request) {
//        return request.toEntity();
//    }
//
//    private CityIntroCreate createCityInfo() {
//        return CityIntroCreate.builder()
//                .name(SE)
//                .category(INTRO)
//                .content("테스트 데이터")
//                .image("test.jpg")
//                .build();
//    }
}
