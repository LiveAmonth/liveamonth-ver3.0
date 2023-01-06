package com.lam.liveamonthapp.controller.document.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import com.lam.liveamonthapp.controller.ApiDocsTest;
import com.lam.liveamonthapp.controller.common.Category;
import com.lam.liveamonthapp.controller.common.DocsResponse;
import com.lam.liveamonthapp.paging.CustomPage;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.lam.liveamonthapp.global.enumMapper.EnumClassConst.*;
import static com.lam.liveamonthapp.util.CookieUtil.addRefreshTokenCookie;
import static com.lam.liveamonthapp.utils.ApiDocumentUtils.*;


public class CommonApiDocsTest extends ApiDocsTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("공통 응답 객체")
    void get_common_response() throws Exception {
        // expected
        ResultActions result = this.mockMvc.perform(get("/docs")
                .accept(APPLICATION_JSON)
        );
        MvcResult mvcResult = result.andReturn();
        Category category = getData(mvcResult);

        result
                .andExpect(status().isOk())
                .andDo(document("common",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        customResponseFields("custom-response", null,
                                attributes(getTitleAttributes("공통 응답")),
                                subsectionWithPath("data").description("데이터"),
                                fieldWithPath("status").type(NUMBER).description("HTTP상태 코드"),
                                fieldWithPath("timeStamp").type(STRING).description("호출 시간"),
                                fieldWithPath("message").type(STRING).description("결과메시지")
                        ),
                        customResponseFields("custom-response",
                                beneathPath("data.pageable").withSubsectionId("pageable"),
                                attributes(getTitleAttributes("공통 페이지응답")),
                                fieldWithPath("first").type(BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("last").type(BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("totalElements").type(NUMBER).description("전체 컨텐츠 수"),
                                fieldWithPath("totalPages").type(NUMBER).description("전체 페이지 수")
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].genderType")
                                        .withSubsectionId("gender-type"),
                                attributes(getTitleAttributes(GENDER_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getGenderType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].accountState")
                                        .withSubsectionId("account-state"),
                                attributes(getTitleAttributes(ACCOUNT_STATE.getValue())),
                                enumConvertFieldDescriptor(category.getAccountState())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].inquiryCategory")
                                        .withSubsectionId("inquiry-category"),
                                attributes(getTitleAttributes(INQUIRY_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getInquiryCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].cityIntroCategory")
                                        .withSubsectionId("city-intro-category"),
                                attributes(getTitleAttributes(CITY_INTRO_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getCityIntroCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].cityName")
                                        .withSubsectionId("city-name"),
                                attributes(getTitleAttributes(CITY_NAME.getValue())),
                                enumConvertFieldDescriptor(category.getCityName())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].transportCategory")
                                        .withSubsectionId("transport-category"),
                                attributes(getTitleAttributes(TRANSPORT_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getTransportCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].transportGrade")
                                        .withSubsectionId("transport-grade"),
                                attributes(getTitleAttributes(TRANSPORT_GRADE.getValue())),
                                enumConvertFieldDescriptor(category.getTransportGrade())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].monthCategory")
                                        .withSubsectionId("month"),
                                attributes(getTitleAttributes(MONTH_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getMonthCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].scheduleSearchType")
                                        .withSubsectionId("schedule-search-type"),
                                attributes(getTitleAttributes(SCHEDULE_SEARCH_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getScheduleSearchType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].scheduleFilterType")
                                        .withSubsectionId("schedule-filter-type"),
                                attributes(getTitleAttributes(SCHEDULE_FILTER_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getScheduleFilterType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].scheduleSortType")
                                        .withSubsectionId("schedule-sort-type"),
                                attributes(getTitleAttributes(SCHEDULE_SORT_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getScheduleSortType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].reviewCategory")
                                        .withSubsectionId("review-category"),
                                attributes(getTitleAttributes(REVIEW_CATEGORY.getValue())),
                                enumConvertFieldDescriptor(category.getReviewCategory())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].reviewSearchGroup")
                                        .withSubsectionId("review-search-group"),
                                attributes(getTitleAttributes(REVIEW_MENU_GROUP.getValue())),
                                enumConvertFieldDescriptor(category.getReviewSearchGroup())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].reviewSearchType")
                                        .withSubsectionId("review-search-type"),
                                attributes(getTitleAttributes(REVIEW_SEARCH_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getReviewSearchType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].reviewSortType")
                                        .withSubsectionId("review-sort-type"),
                                attributes(getTitleAttributes(REVIEW_SORT_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getReviewSortType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].interactionType")
                                        .withSubsectionId("interaction-type"),
                                attributes(getTitleAttributes(INTERACTION_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getInteractionType())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].interactionState")
                                        .withSubsectionId("interaction-state"),
                                attributes(getTitleAttributes(INTERACTION_STATE.getValue())),
                                enumConvertFieldDescriptor(category.getInteractionState())
                        ),
                        customResponseFields("custom-response", beneathPath("data.content[].commentType")
                                        .withSubsectionId("comment-type"),
                                attributes(getTitleAttributes(COMMENT_TYPE.getValue())),
                                enumConvertFieldDescriptor(category.getCommentType())
                        )
                ));
    }

    @Test
    @DisplayName("Auth Request Header")
    void common_other_request_header() throws Exception {
        ResultActions result = this.mockMvc.perform(get("/docs")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .header("Authorization", "{access_token}")
                .cookie(addRefreshTokenCookie("{refresh_token}"))
        );

        result.andDo(
                document("auth-header",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("로그인 시 발급 받은 엑세스 토큰")
                        )
                )
        );
    }

    Category getData(MvcResult result) throws IOException {
        DocsResponse<CustomPage<Category>> apiResponseDto =
                objectMapper.readValue(
                        result.getResponse().getContentAsByteArray(),
                        new TypeReference<>() {
                        });

        return apiResponseDto.getData().getContent().get(0);
    }
}
