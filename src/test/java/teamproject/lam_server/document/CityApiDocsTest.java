package teamproject.lam_server.document;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.init.service.InitCityService;

import java.util.Random;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentRequest;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentResponse;
import static teamproject.lam_server.utils.DocumentFormatGenerator.getDateTimeFormat;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("local")
class CityApiDocsTest {

    private MockMvc mockMvc;
    @Autowired
    private InitCityService initCityService;

    private CityName cityName;

    @BeforeAll
    public void setUp() {
        initCityService.initCityIntroData();
        initCityService.initCityTransportData();
        initCityService.initCityWeatherData();
    }

    @BeforeEach
    public void setUpParam(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
        cityName = CityName.values()[(new Random()).nextInt(CityName.values().length)];
    }

    @Test
    @DisplayName("도시 그리드 이미지 조회")
    void get_city_grid_info() throws Exception {
        // expected
        this.mockMvc.perform(get("/api/v1/city/grid-infos")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-city-grid-contents",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").type(NUMBER).description("HTTP 상태 코드"),
                                fieldWithPath("message").type(STRING).description("결과 메시지"),
                                fieldWithPath("timeStamp").type(STRING).attributes(getDateTimeFormat()).description("결과 시간"),
                                fieldWithPath("data.[].name.code").type(STRING).description("도시 코드"),
                                fieldWithPath("data.[].name.value").type(STRING).description("도시 이름"),
                                fieldWithPath("data.[].image").type(STRING).description("이미지 주소(uri)"),
                                fieldWithPath("data.[].averageDegree").type(NUMBER).description("평균 기온"),
                                fieldWithPath("data.[].transportScore").type(NUMBER).description("교통 점수")
                        )
                ));
    }

    @Test
    @DisplayName("도시 소개 정보 조회")
    void get_city_intro() throws Exception {
        // expected
        this.mockMvc.perform(get("/api/v1/city/{cityName}", cityName)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-city-intro",
                        pathParameters(
                                parameterWithName("cityName").description("도시 이름")
                        )
                ));
    }

    @Test
    @DisplayName("도시 추가 정보 조회")
    void get_extra_city_info() throws Exception {
        // expected
        this.mockMvc.perform(get("/api/v1/city/{cityName}/extra", cityName)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-extra-info",
                        pathParameters(
                                parameterWithName("cityName").description("도시 이름")
                        )
                ));
    }

    @Test
    @DisplayName("먹거리 볼거리 정보 조회")
    void get_city_food_and_view_info() throws Exception {
        // expected
        this.mockMvc.perform(get("/api/v1/city/{cityName}/food-and-view", cityName)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("get-food-and-view",
                        pathParameters(
                                parameterWithName("cityName").description("도시 이름")
                        )
                ));
    }
}


