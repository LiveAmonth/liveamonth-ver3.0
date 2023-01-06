package com.lam.liveamonthapp.controller.document.admin;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.annotaiton.WithMockCustomUser;
import com.lam.liveamonthapp.controller.ApiDocsTest;
import com.lam.liveamonthapp.domain.inqiury.constants.InquiryCategory;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryAnswerCreate;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryAnswerEdit;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryCreate;
import com.lam.liveamonthapp.domain.inqiury.entity.Inquiry;
import com.lam.liveamonthapp.domain.inqiury.entity.InquiryAnswer;
import com.lam.liveamonthapp.domain.inqiury.repository.core.InquiryAnswerRepository;
import com.lam.liveamonthapp.domain.inqiury.repository.core.InquiryRepository;
import com.lam.liveamonthapp.domain.member.constants.GenderType;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.lam.liveamonthapp.domain.member.constants.Role.MANAGER;
import static com.lam.liveamonthapp.util.CookieUtil.addRefreshTokenCookie;
import static com.lam.liveamonthapp.utils.ApiDocumentUtils.*;
import static com.lam.liveamonthapp.utils.ApiDocumentUtils.getConstraintAttributes;

@Transactional
public class InquiryAdminDocsTest extends ApiDocsTest {

    static final String BASIC_URL = "/admin/v1/inquiries";

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
                .gender(GenderType.MALE.name())
                .birth(LocalDate.now().minusDays(1))
                .build();
        Member saveUser = memberRepository.save(memberCreate.toEntity(passwordEncoder));
        Inquiry saveInquiry = inquiryRepository.save(inquiryCreate.toEntity(saveUser));

        InquiryAnswerCreate inquiryAnswerCreate = InquiryAnswerCreate.builder()
                .content("1:1문의 답변")
                .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(InquiryAnswerCreate.class);

        // when
        ResultActions result = this.mockMvc.perform(post(BASIC_URL + "/{inquiry_id}", saveInquiry.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(objectMapper.writeValueAsString(inquiryAnswerCreate)))
                .andExpect(status().isOk());

        result.andDo(document("inquiry-answer-post",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("inquiry_id").description("1:1문의 id")
                ),
                requestFields(
                        fieldWithPath("content").type(STRING)
                                .description("내용")
                                .attributes(getConstraintAttributes(constraints, "content"))
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
                .gender(GenderType.MALE.name())
                .birth(LocalDate.now().minusDays(1))
                .build();
        Member saveUser = memberRepository.save(memberCreate.toEntity(passwordEncoder));
        Inquiry saveInquiry = inquiryRepository.save(inquiryCreate.toEntity(saveUser));

        InquiryAnswerCreate inquiryAnswerCreate = InquiryAnswerCreate.builder()
                .content("1:1문의 답변")
                .build();
        InquiryAnswer saveAnswer = inquiryAnswerRepository.save(inquiryAnswerCreate.toEntity());
        saveInquiry.answerInquiry(saveAnswer);

        InquiryAnswerEdit inquiryAnswerEdit = InquiryAnswerEdit.builder().content("1:1문의 답변 수정").build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(InquiryAnswerEdit.class);

        // when
        ResultActions result = this.mockMvc.perform(patch(BASIC_URL+"/{answer_id}", saveAnswer.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(objectMapper.writeValueAsString(inquiryAnswerEdit)))
                .andExpect(status().isOk());

        // then
        result.andDo(document("inquiry-answer-patch",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("answer_id").description("1:1문의 답변 id")
                ),
                requestFields(
                        fieldWithPath("content").type(STRING)
                                .description("내용")
                                .attributes(getConstraintAttributes(constraints, "content"))
                )
        ));

    }
}
