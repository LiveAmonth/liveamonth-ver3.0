package teamproject.lam_server.controller.document.api;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import teamproject.lam_server.controller.ApiDocsTest;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;
import teamproject.lam_server.domain.city.constants.TransportCategory;
import teamproject.lam_server.init.service.InitCityService;

import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.domain.city.constants.CityIntroCategory.*;
import static teamproject.lam_server.domain.city.constants.CityName.*;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.*;
import static teamproject.lam_server.utils.ApiDocumentUtils.*;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateLinkCode;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CityApiDocsTest extends ApiDocsTest {
    static final String BASIC_URL = "/api/v1/city";
    @Autowired
    InitCityService initCityService;

    CityName cityName;

    @BeforeAll
    public void setUp() {
        initCityService.initCityIntroData();
        initCityService.initCityTransportData();
        initCityService.initCityWeatherData();
    }

    @BeforeEach
    void setUpCityName() {
        cityName = CityName.values()[(new Random()).nextInt(CityName.values().length)];
    }

    @Test
    @DisplayName("도시 요약 정보 조회")
    void get_city_summary() throws Exception {
        // when
        ResultActions result = this.mockMvc.perform(get(BASIC_URL + "/grid-infos")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(CityName.values().length)))
                .andExpect(jsonPath("$..['name']").exists())
                .andExpect(jsonPath("$..['image']").exists())
                .andExpect(jsonPath("$..['averageDegree']").exists())
                .andExpect(jsonPath("$..['transportScore']").exists())
                .andExpect(jsonPath("$..name[?(@.code == '%s')]", SE.name()).exists())
                .andExpect(jsonPath("$..name[?(@.code == '%s')]", GN.name()).exists())
                .andExpect(jsonPath("$..name[?(@.code == '%s')]", GJ.name()).exists())
                .andExpect(jsonPath("$..name[?(@.code == '%s')]", BS.name()).exists())
                .andExpect(jsonPath("$..name[?(@.code == '%s')]", YS.name()).exists())
                .andExpect(jsonPath("$..name[?(@.code == '%s')]", JJ.name()).exists());

        // then
        result.andDo(document("city-summary-get",
                getDocumentRequest(),
                getDocumentResponse(),
                customResponseFields("response",
                        beneathPath("data[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("City Summary Response Fields")),
                        enumCodeFieldWithPath("name", CITY_NAME),
                        enumValueFieldWithPath("name", CITY_NAME),
                        fieldWithPath("image").type(STRING).description("이미지 주소(uri)"),
                        fieldWithPath("averageDegree").type(NUMBER).description("평균 기온"),
                        fieldWithPath("transportScore").type(NUMBER).description("교통 점수")
                )
        ));
    }

    @Test
    @DisplayName("도시 소개 정보 조회")
    void get_city_intro() throws Exception {
        // when
        ResultActions result = this.mockMvc.perform(get(BASIC_URL + "/{cityName}", cityName)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$..['%s']", INTRO).isArray())
                .andExpect(jsonPath("$..['%s']", FOOD).isArray())
                .andExpect(jsonPath("$..['%s']", VIEW).isArray());

        // then
        result.andDo(document("city-intro-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("cityName").description(generateLinkCode(CITY_NAME))
                ),
                customResponseFields("response",
                        beneathPath("data.FOOD[]").withSubsectionId("image-content"),
                        attributes(getTitleAttributes("City Image-Content Response Fields")),
                        fieldWithPath("content").type(STRING).description("이미지 설명"),
                        fieldWithPath("image").type(STRING).description("이미지(uri)")
                )
        ));
    }

    @Test
    @DisplayName("도시 추가 정보 조회")
    void get_extra_city_info() throws Exception {
        // when
        ResultActions result = this.mockMvc.perform(get(BASIC_URL + "/{cityName}/extra", cityName)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..['%s']", "transports").isArray())
                .andExpect(jsonPath("$..['%s']", "weathers").isArray())
                .andExpect(jsonPath("$..['transports'].length()").value(TransportCategory.values().length))
                .andExpect(jsonPath("$..['weathers'].length()").value(MonthCategory.values().length));

        // given
        result.andDo(document("city-extra-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("cityName").description(generateLinkCode(CITY_NAME))
                ),
                customResponseFields("response",
                        beneathPath("data.transports[]").withSubsectionId("transport"),
                        attributes(getTitleAttributes("City Transport Response Fields")),
                        idFieldWithPath("transport id"),
                        enumCodeFieldWithPath("name", CITY_NAME),
                        enumValueFieldWithPath("name", CITY_NAME),
                        enumCodeFieldWithPath("category", TRANSPORT_CATEGORY),
                        enumValueFieldWithPath("category", TRANSPORT_CATEGORY),
                        fieldWithPath("stationCount").type(NUMBER).description("개수"),
                        fieldWithPath("score").type(NUMBER).description("점수")
                ),
                customResponseFields("response",
                        beneathPath("data.weathers[]").withSubsectionId("weather"),
                        attributes(getTitleAttributes("City Weather Response Fields")),
                        idFieldWithPath("weather id"),
                        enumCodeFieldWithPath("name", CITY_NAME),
                        enumValueFieldWithPath("name", CITY_NAME),
                        enumCodeFieldWithPath("month", MONTH_CATEGORY),
                        enumValueFieldWithPath("month", MONTH_CATEGORY),
                        fieldWithPath("maxDegree").type(NUMBER).description("최고 기온"),
                        fieldWithPath("minDegree").type(NUMBER).description("최저 기온"),
                        fieldWithPath("averageDegree").type(NUMBER).description("평균 기온")
                )

        ));

    }
}


