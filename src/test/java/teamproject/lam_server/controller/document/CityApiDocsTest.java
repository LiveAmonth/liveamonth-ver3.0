package teamproject.lam_server.controller.document;

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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentRequest;
import static teamproject.lam_server.utils.ApiDocumentUtils.getDocumentResponse;


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
    public void setUpParam(
            final WebApplicationContext context,
            final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .build();
        cityName = CityName.values()[(new Random()).nextInt(CityName.values().length)];
    }

    @Test
    @DisplayName("도시 요약 정보 조회")
    void get_city_summary() throws Exception {
        // expected
        this.mockMvc.perform(get("/api/v1/city/grid-infos")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("city-summary-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(beneathPath("data[]").withSubsectionId("data"),
                                fieldWithPath("name.code").type(STRING).description("도시 코드"),
                                fieldWithPath("name.value").type(STRING).description("도시 이름"),
                                fieldWithPath("image").type(STRING).description("이미지 주소(uri)"),
                                fieldWithPath("averageDegree").type(NUMBER).description("평균 기온"),
                                fieldWithPath("transportScore").type(NUMBER).description("교통 점수")
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
                .andDo(document("city-intro-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("cityName").description("도시 이름")
                        ),
                        responseFields(
                                beneathPath("data.FOOD[]").withSubsectionId("image-content"),
                                fieldWithPath("content").type(STRING).description("이미지 설명"),
                                fieldWithPath("image").type(STRING).description("이미지(uri)")
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
                .andDo(document("city-extra-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("cityName").description("도시 이름")
                        ),
                        responseFields(
                                beneathPath("data.transports[]").withSubsectionId("transport"),
                                fieldWithPath("id").type(NUMBER).description("id"),
                                fieldWithPath("name.code").type(STRING).description("도시 코드"),
                                fieldWithPath("name.value").type(STRING).description("도시 이름"),
                                fieldWithPath("category.code").type(STRING).description("카테고리 코드"),
                                fieldWithPath("category.value").type(STRING).description("카테고리 이름"),
                                fieldWithPath("stationCount").type(NUMBER).description("개수"),
                                fieldWithPath("score").type(NUMBER).description("점수")
                        ),
                        responseFields(
                                beneathPath("data.weathers[]").withSubsectionId("weather"),
                                fieldWithPath("id").type(NUMBER).description("id"),
                                fieldWithPath("name.code").type(STRING).description("도시 코드"),
                                fieldWithPath("name.value").type(STRING).description("도시 이름"),
                                fieldWithPath("month").type(STRING).description("월"),
                                fieldWithPath("maxDegree").type(NUMBER).description("최고 기온"),
                                fieldWithPath("minDegree").type(NUMBER).description("최저 기온"),
                                fieldWithPath("averageDegree").type(NUMBER).description("평균 기온")
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
                .andDo(document("city-food-and-view-get",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("cityName").description("도시 이름")
                        ),
                        responseFields(
                                beneathPath("data.foodInfos[]").withSubsectionId("image-content"),
                                fieldWithPath("content").type(STRING).description("이미지 설명"),
                                fieldWithPath("image").type(STRING).description("이미지(uri)")
                        )
                ));
    }
}


