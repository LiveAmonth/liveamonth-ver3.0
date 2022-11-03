package teamproject.lam_server.controller.document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import teamproject.lam_server.controller.common.Category;
import teamproject.lam_server.controller.common.DocsResponse;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.*;
import static teamproject.lam_server.utils.ApiDocumentUtils.customResponseFields;
import static teamproject.lam_server.utils.ApiDocumentUtils.enumConvertFieldDescriptor;


@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("local")
public class CommonDocsTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(
            final WebApplicationContext context,
            final RestDocumentationContextProvider provider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(provider))
                .build();
    }

    @Test
    @DisplayName("공통 응답 객체")
    void get_common_response() throws Exception {
        // expected

        ResultActions result = this.mockMvc.perform(get("/docs")
                .accept(APPLICATION_JSON));
        MvcResult mvcResult = result.andReturn();
        Category category = getData(mvcResult);

        result
                .andExpect(status().isOk())
                .andDo(document("common",
                        customResponseFields("custom-response", null,
                                attributes(key("title").value("공통응답")),
                                subsectionWithPath("data").description("데이터"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("HTTP상태 코드"),
                                fieldWithPath("timeStamp").type(JsonFieldType.STRING).description("호출 시간"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("결과메시지")
                        ),
                        customResponseFields("custom-response", beneathPath("data.genderType").withSubsectionId("gender-type"),
                                attributes(key("title").value(GENDER_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getGenderType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.accountState").withSubsectionId("account-state"),
                                attributes(key("title").value(ACCOUNT_STATE.getValue())),
                                enumConvertFieldDescriptor(category.getAccountState())
                        ),
                        customResponseFields("custom-response", beneathPath("data.inquiryCategory").withSubsectionId("inquiry-category"),
                                attributes(key("title").value(INQUIRY_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getInquiryCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.cityIntroCategory").withSubsectionId("city-intro-category"),
                                attributes(key("title").value(CITY_INTRO_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getCityIntroCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.cityName").withSubsectionId("city-name"),
                                attributes(key("title").value(CITY_NAME.getValue())),
                                enumConvertFieldDescriptor(category.getCityName())
                        ),
                        customResponseFields("custom-response", beneathPath("data.transportCategory").withSubsectionId("transport-category"),
                                attributes(key("title").value(TRANSPORT_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getTransportCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.transportGrade").withSubsectionId("transport-grade"),
                                attributes(key("title").value(TRANSPORT_GRADE.getValue())),
                                enumConvertFieldDescriptor(category.getTransportGrade())
                        ),
                        customResponseFields("custom-response", beneathPath("data.monthCategory").withSubsectionId("month"),
                                attributes(key("title").value(MONTH_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getMonthCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.scheduleSearchType").withSubsectionId("schedule-search-type"),
                                attributes(key("title").value(SCHEDULE_SEARCH_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getScheduleSearchType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.scheduleFilterType").withSubsectionId("schedule-filter-type"),
                                attributes(key("title").value(SCHEDULE_FILTER_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getScheduleFilterType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.scheduleSortType").withSubsectionId("schedule-sort-type"),
                                attributes(key("title").value(SCHEDULE_SORT_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getScheduleSortType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.reviewCategory").withSubsectionId("review-category"),
                                attributes(key("title").value(REVIEW_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getReviewCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.reviewSearchGroup").withSubsectionId("review-search-group"),
                                attributes(key("title").value(REVIEW_MENU_GROUP.getValue())),
                                enumConvertFieldDescriptor(category.getReviewSearchGroup())
                        ),
                        customResponseFields("custom-response", beneathPath("data.reviewSearchType").withSubsectionId("review-search-type"),
                                attributes(key("title").value(REVIEW_SEARCH_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getReviewSearchType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.reviewSortType").withSubsectionId("review-sort-type"),
                                attributes(key("title").value(REVIEW_SORT_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getReviewSortType())
                        )
                ));
    }

    Category getData(MvcResult result) throws IOException {
        DocsResponse<Category> apiResponseDto =
                objectMapper.readValue(
                        result.getResponse().getContentAsByteArray(),
                        new TypeReference<DocsResponse<Category>>() {
                        });

        return apiResponseDto.getData();
    }
}
