package teamproject.lam_server.controller.document.api;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.annotaiton.WithMockCustomUser;
import teamproject.lam_server.controller.ApiDocsTest;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryAnswerCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryEdit;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.inqiury.entity.InquiryAnswer;
import teamproject.lam_server.domain.inqiury.repository.core.InquiryAnswerRepository;
import teamproject.lam_server.domain.inqiury.repository.core.InquiryRepository;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.global.service.SecurityContextFinder;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.INQUIRY_CATEGORY;
import static teamproject.lam_server.util.CookieUtil.addRefreshTokenCookie;
import static teamproject.lam_server.utils.ApiDocumentUtils.*;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateLinkCode;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateValue;

public class InquiryApiDocsTest extends ApiDocsTest {
    static final String BASIC_URL = "/api/v1/inquiries";
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    SecurityContextFinder finder;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    InquiryRepository inquiryRepository;
    @Autowired
    InquiryAnswerRepository inquiryAnswerRepository;

    @AfterEach
    void clear() {
        inquiryRepository.deleteAll();
    }

    @Test
    @DisplayName("1:1문의 작성")
    @WithMockCustomUser
    void write_inquiry() throws Exception {
        // given
        InquiryCreate request = InquiryCreate.builder()
                .title("1:1문의 테스트 제목")
                .category(InquiryCategory.ETC.name())
                .content("1:1문의 테스트 내용")
                .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(InquiryCreate.class);

        // when
        ResultActions result = this.mockMvc.perform(post(BASIC_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}")))
                .andExpect(status().isOk());

        // then
        result.andDo(document("inquiry-post",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        titleFieldWithPath()
                                .attributes(getConstraintAttributes(constraints, "title")),
                        contentFieldWithPath().attributes(getConstraintAttributes(constraints, "content")),
                        fieldWithPath("category").type(STRING)
                                .description(generateLinkCode(INQUIRY_CATEGORY))
                                .attributes(getConstraintAttributes(constraints, "category"))
                ),
                getPostResponseFieldsSnippet()
        ));
    }

    @Test
    @DisplayName("1:1문의 다건 조회")
    @WithMockCustomUser
    void get_inquiry_list() throws Exception {
        Inquiry inquiry1 = InquiryCreate.builder()
                .title("1:1문의 테스트 제목1")
                .category(InquiryCategory.SCHEDULE.name())
                .content("1:1문의 테스트 내용1")
                .build()
                .toEntity(finder.getLoggedInMember());
        Inquiry inquiry2 = InquiryCreate.builder()
                .title("1:1문의 테스트 제목2")
                .category(InquiryCategory.ETC.name())
                .content("1:1문의 테스트 내용2")
                .build()
                .toEntity(finder.getLoggedInMember());
        inquiryRepository.saveAll(List.of(inquiry1, inquiry2));

        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/list")
                                .param("page", "0")
                                .param("size", "10")
                                .param("sorts", "id,desc")
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(2)))
                .andExpect(jsonPath("$..[?(@.title == '%s')]", inquiry1.getTitle()).exists())
                .andExpect(jsonPath("$..category[?(@.code == '%s')]", inquiry2.getCategory().getCode()).exists());

        result.andDo(document("inquiry-list-get",
                getDocumentRequest(),
                getDocumentResponse(),
                getPageableRequestSnippet(),
                customResponseFields("format-response",
                        beneathPath("data.content[]").withSubsectionId("content"),
                        attributes(getTitleAttributes("Inquiry List Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        writerFieldWithPath(),
                        fieldWithPath("category.code").type(STRING).description(generateLinkCode(INQUIRY_CATEGORY)),
                        fieldWithPath("category.value").type(STRING).description(generateValue(INQUIRY_CATEGORY)),
                        fieldWithPath("answered").type(BOOLEAN).description("답변 여부"),
                        dateTimeFieldWithPath("date", "작성 시간")
                )
        ));
    }

    @Test
    @DisplayName("1:1문의 단건 조회")
    @Transactional
    @WithMockCustomUser
    void get_inquiry() throws Exception {
        Inquiry inquiry = InquiryCreate.builder()
                .title("1:1문의 테스트 제목")
                .category(InquiryCategory.SCHEDULE.name())
                .content("1:1문의 테스트 내용")
                .build()
                .toEntity(finder.getLoggedInMember());
        Inquiry saveInquiry = inquiryRepository.save(inquiry);

        InquiryAnswerCreate inquiryAnswerCreate = InquiryAnswerCreate.builder()
                .content("1:1문의 답변")
                .build();
        InquiryAnswer saveAnswer = inquiryAnswerRepository.save(inquiryAnswerCreate.toEntity());
        saveInquiry.answerInquiry(saveAnswer);

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/{id}", saveInquiry.getId())
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value(inquiry.getTitle()))
                .andExpect(jsonPath("$.data.category.code").value(inquiry.getCategory().getCode()))
                .andExpect(jsonPath("$.data.content").value(inquiry.getContent()))
                .andExpect(jsonPath("$.data.answer.id").value(inquiry.getAnswer().getId()));


        // then
        result.andDo(document("inquiry-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("id").description("1:1문의 id")
                ),
                customResponseFields("format-response",
                        beneathPath("data").withSubsectionId("data"),
                        attributes(getTitleAttributes("Inquiry Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        writerFieldWithPath(),
                        contentFieldWithPath(),
                        enumCodeFieldWithPath("category", INQUIRY_CATEGORY),
                        enumValueFieldWithPath("category", INQUIRY_CATEGORY),
                        fieldWithPath("answered").type(BOOLEAN).description("답변 여부"),
                        dateTimeFieldWithPath("dateTime", "작성 시간"),
                        subsectionWithPath("answer").description("답변")
                ),
                customResponseFields("format-response",
                        beneathPath("data.answer").withSubsectionId("answer"),
                        attributes(getTitleAttributes("1:1문의 답변")),
                        idFieldWithPath(),
                        writerFieldWithPath(),
                        contentFieldWithPath(),
                        dateTimeFieldWithPath("dateTime", "작성 시간")
                )
        ));
    }

    @Test
    @DisplayName("1:1문의 수정")
    @WithMockCustomUser
    void edit_inquiry() throws Exception {
        // given
        Inquiry inquiry = InquiryCreate.builder()
                .title("1:1문의 테스트 제목")
                .category(InquiryCategory.SCHEDULE.name())
                .content("1:1문의 테스트 내용")
                .build()
                .toEntity(finder.getLoggedInMember());
        Inquiry saveInquiry = inquiryRepository.save(inquiry);

        InquiryEdit editedInquiry = InquiryEdit.builder()
                .title("변경된 제목")
                .content("변경된 내용")
                .category(InquiryCategory.REVIEW.name())
                .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(InquiryEdit.class);

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/{id}", saveInquiry.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(objectMapper.writeValueAsString(editedInquiry))
        ).andExpect(status().isOk());

        result.andDo(document("inquiry-patch",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("id").description("1:1문의 id")
                ),
                requestFields(
                        titleFieldWithPath().attributes(getConstraintAttributes(constraints, "title")),
                        contentFieldWithPath().attributes(getConstraintAttributes(constraints, "content")),
                        fieldWithPath("category").type(STRING)
                                .description(generateLinkCode(INQUIRY_CATEGORY))
                                .attributes(getConstraintAttributes(constraints, "category"))
                )
        ));
    }

    @Test
    @DisplayName("1:1문의 삭제")
    @WithMockCustomUser
    void delete_inquiry() throws Exception {
        // given
        Inquiry inquiry = InquiryCreate.builder()
                .title("1:1문의 테스트 제목")
                .category(InquiryCategory.SCHEDULE.name())
                .content("1:1문의 테스트 내용")
                .build()
                .toEntity(finder.getLoggedInMember());
        Inquiry saveInquiry = inquiryRepository.save(inquiry);


        // when
        ResultActions result =
                this.mockMvc.perform(
                        delete(BASIC_URL + "/{id}", saveInquiry.getId())
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                ).andExpect(status().isOk());

        // then
        result.andDo(document("inquiry-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("id").description("1:1문의 id")
                )
        ));
    }
}
