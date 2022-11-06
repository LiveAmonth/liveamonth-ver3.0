package teamproject.lam_server.controller.document;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.annotaiton.WithMockCustomUser;
import teamproject.lam_server.controller.ApiDocsTest;
import teamproject.lam_server.domain.inqiury.constants.InquiryCategory;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryAnswerCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryAnswerEdit;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryEdit;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.inqiury.entity.InquiryAnswer;
import teamproject.lam_server.domain.inqiury.repository.InquiryAnswerRepository;
import teamproject.lam_server.domain.inqiury.repository.InquiryRepository;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.constants.Role;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.global.service.SecurityContextFinder;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.domain.member.constants.Role.MANAGER;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.INQUIRY_CATEGORY;
import static teamproject.lam_server.utils.ApiDocumentUtils.*;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateLinkCode;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateValue;
import static teamproject.lam_server.utils.DocumentFormatGenerator.getDateTimeFormat;

@Transactional
public class InquiryApiDocsTest extends ApiDocsTest {

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
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("1:1문의 작성")
    @WithMockCustomUser(loginId = "inquiry", role = Role.USER)
    void write_inquiry() throws Exception {
        InquiryCreate inquiryCreate = InquiryCreate.builder()
                .title("1:1문의 테스트 제목")
                .category(InquiryCategory.ETC.name())
                .content("1:1문의 테스트 내용")
                .build();

        ResultActions result = this.mockMvc.perform(post("/api/v1/inquiries")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inquiryCreate)))
                .andExpect(status().isOk());

        result.andDo(document("inquiry-post",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("category").type(STRING).description(generateLinkCode(INQUIRY_CATEGORY))
                )
        ));
    }

    @Test
    @DisplayName("1:1문의 다건 조회")
    @WithMockCustomUser(loginId = "inquiry", role = Role.USER)
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

        ResultActions result = this.mockMvc.perform(get("/api/v1/inquiries/list")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sorts", "id,desc")
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        result.andDo(document("inquiry-list-get",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("컨텐츠 수"),
                        parameterWithName("sorts").description("정렬 옵션")
                ),
                responseFields(
                        beneathPath("data.content[]").withSubsectionId("content"),
                        fieldWithPath("id").type(NUMBER).description("id"),
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("writer").type(STRING).description("작성자"),
                        fieldWithPath("category.code").type(STRING).description(generateLinkCode(INQUIRY_CATEGORY)),
                        fieldWithPath("category.value").type(STRING).description(generateValue(INQUIRY_CATEGORY)),
                        fieldWithPath("answered").type(BOOLEAN).description("답변 여부"),
                        fieldWithPath("date").type(STRING).attributes(getDateTimeFormat()).description("작성 시간")
                )
        ));
    }

    @Test
    @DisplayName("1:1문의 단건 조회")
    @WithMockCustomUser(loginId = "inquiry", role = Role.USER)
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

        ResultActions result = this.mockMvc.perform(get("/api/v1/inquiries/{id}", saveInquiry.getId()))
                .andExpect(status().isOk());

        result.andDo(document("inquiry-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("id").description("1:1문의 id")
                ),
                responseFields(
                        beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("id").type(NUMBER).description("id"),
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("writer").type(STRING).description("작성자"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("category.code").type(STRING).description(generateLinkCode(INQUIRY_CATEGORY)),
                        fieldWithPath("category.value").type(STRING).description(generateValue(INQUIRY_CATEGORY)),
                        fieldWithPath("answered").type(BOOLEAN).description("답변 여부"),
                        fieldWithPath("dateTime").type(STRING).attributes(getDateTimeFormat()).description("작성 시간"),
                        subsectionWithPath("answer").description("답변")
                ),
                customResponseFields("response",
                        beneathPath("data.answer").withSubsectionId("answer"),
                        attributes(key("title").value("1:1문의 답변")),
                        fieldWithPath("id").type(NUMBER).description("id"),
                        fieldWithPath("writer").type(STRING).description("관리자"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("dateTime").type(STRING).attributes(getDateTimeFormat()).description("작성 시간")
                )
        ));
    }

    @Test
    @DisplayName("1:1문의 수정")
    @WithMockCustomUser(loginId = "inquiry", role = Role.USER)
    void edit_inquiry() throws Exception {
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

        ResultActions result = this.mockMvc.perform(patch("/api/v1/inquiries/{id}", saveInquiry.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(editedInquiry)))
                .andExpect(status().isOk());

        result.andDo(document("inquiry-patch",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("id").description("1:1문의 id")
                ),
                requestFields(
                        fieldWithPath("title").type(STRING).description("제목"),
                        fieldWithPath("content").type(STRING).description("내용"),
                        fieldWithPath("category").type(STRING).description(generateLinkCode(INQUIRY_CATEGORY))
                )
        ));
    }

    @Test
    @DisplayName("1:1문의 삭제")
    @WithMockCustomUser(loginId = "inquiry", role = Role.USER)
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
                this.mockMvc.perform(delete("/api/v1/inquiries/{id}",
                                saveInquiry.getId())).
                        andExpect(status().isOk());

        // then
        result.andDo(document("inquiry-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("id").description("1:1문의 id")
                )
        ));
    }

    @Test
    @DisplayName("1:1문의 답변 작성")
    @WithMockCustomUser(loginId = "manager", role = MANAGER)
    void answer_inquiry() throws Exception {
        // given
        InquiryCreate inquiryCreate = InquiryCreate.builder()
                .title("1:1문의 테스트 제목")
                .category(InquiryCategory.ETC.name())
                .content("1:1문의 테스트 내용")
                .build();
        MemberCreate memberCreate = MemberCreate.builder()
                .loginId("testMember")
                .nickname("testMember")
                .name("testMember")
                .password("testMember1!")
                .email("testMember@gmail.com")
                .gender(GenderType.MALE)
                .birth(LocalDate.now())
                .build();
        Member saveUser = memberRepository.save(memberCreate.toEntity(passwordEncoder));
        Inquiry saveInquiry = inquiryRepository.save(inquiryCreate.toEntity(saveUser));

        InquiryAnswerCreate inquiryAnswerCreate = InquiryAnswerCreate.builder()
                .content("1:1문의 답변")
                .build();

        // when
        ResultActions result = this.mockMvc.perform(post("/admin/v1/inquiries/{inquiryId}", saveInquiry.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inquiryAnswerCreate)))
                .andExpect(status().isOk());

        result.andDo(document("inquiry-answer-post",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("inquiryId").description("1:1문의 id")
                ),
                requestFields(
                        fieldWithPath("content").type(STRING).description("내용")
                )
        ));
    }

    @Test
    @DisplayName("1:1문의 답변 수정")
    @WithMockCustomUser(loginId = "manager", role = MANAGER)
    void edit_inquiry_answer() throws Exception {
        // given
        InquiryCreate inquiryCreate = InquiryCreate.builder()
                .title("1:1문의 테스트 제목")
                .category(InquiryCategory.ETC.name())
                .content("1:1문의 테스트 내용")
                .build();
        MemberCreate memberCreate = MemberCreate.builder()
                .loginId("testMember")
                .nickname("testMember")
                .name("testMember")
                .password("testMember1!")
                .email("testMember@gmail.com")
                .gender(GenderType.MALE)
                .birth(LocalDate.now())
                .build();
        Member saveUser = memberRepository.save(memberCreate.toEntity(passwordEncoder));
        Inquiry saveInquiry = inquiryRepository.save(inquiryCreate.toEntity(saveUser));

        InquiryAnswerCreate inquiryAnswerCreate = InquiryAnswerCreate.builder()
                .content("1:1문의 답변")
                .build();
        InquiryAnswer saveAnswer = inquiryAnswerRepository.save(inquiryAnswerCreate.toEntity());
        saveInquiry.answerInquiry(saveAnswer);

        InquiryAnswerEdit inquiryAnswerEdit = InquiryAnswerEdit.builder().content("1:1문의 답변 수정").build();

        // when
        ResultActions result = this.mockMvc.perform(patch("/admin/v1/inquiries/{answerId}", saveAnswer.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inquiryAnswerEdit)))
                .andExpect(status().isOk());

        // then
        result.andDo(document("inquiry-answer-patch",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("answerId").description("1:1문의 답변 id")
                ),
                requestFields(
                        fieldWithPath("content").type(STRING).description("내용")
                )
        ));
    }
}
