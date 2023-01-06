package com.lam.liveamonthapp.controller.document.admin;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.annotaiton.WithMockCustomUser;
import com.lam.liveamonthapp.controller.ApiDocsTest;
import com.lam.liveamonthapp.domain.member.constants.GenderType;
import com.lam.liveamonthapp.domain.member.constants.Role;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.lam.liveamonthapp.global.enumMapper.EnumClassConst.GENDER_TYPE;
import static com.lam.liveamonthapp.util.CookieUtil.addRefreshTokenCookie;
import static com.lam.liveamonthapp.utils.ApiDocumentUtils.*;
import static com.lam.liveamonthapp.utils.DocsLinkGenerator.generateLinkCode;

@Transactional
public class MemberAdminDocsTest extends ApiDocsTest {

    static final String BASIC_URL = "/admin/v1/members";

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("매니저 생성")
    @WithMockCustomUser(role = Role.ADMIN)
    void create_manager() throws Exception {
        MemberCreate request =
                MemberCreate.builder()
                        .loginId("manager")
                        .password("managerPassword1!")
                        .name("manager")
                        .nickname("manager")
                        .email("manager@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(MemberCreate.class);

        // when
        ResultActions result = this.mockMvc.perform(
                post(BASIC_URL + "/manager/create")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(this.objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("manager-create",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("loginId").type(STRING).description("로그인 아이디")
                                .attributes(getConstraintAttributes(constraints, "loginId")),
                        fieldWithPath("password").type(STRING).description("비밀번호")
                                .attributes(getConstraintAttributes(constraints, "password")),
                        fieldWithPath("name").type(STRING).description("이름")
                                .attributes(getConstraintAttributes(constraints, "name")),
                        fieldWithPath("nickname").type(STRING).description("닉네임")
                                .attributes(getConstraintAttributes(constraints, "nickname")),
                        fieldWithPath("email").type(STRING).description("이메일")
                                .attributes(getConstraintAttributes(constraints, "email")),
                        fieldWithPath("birth").type(STRING).description("생년월일")
                                .attributes(getConstraintAttributes(constraints, "birth")),
                        fieldWithPath("gender").type(STRING).description(generateLinkCode(GENDER_TYPE))
                                .attributes(getConstraintAttributes(constraints, "gender"))
                ),
                getPostResponseFieldsSnippet()
        ));
    }

    @Test
    @DisplayName("회원 데이터 삭제")
    @WithMockCustomUser(role = Role.MANAGER)
    void delete_member() throws Exception {
        // given
        MemberCreate create =
                MemberCreate.builder()
                        .loginId("user")
                        .password("userPassword1!")
                        .name("user")
                        .nickname("user")
                        .email("user@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();

        Member savedMember = memberRepository.save(create.toEntity(passwordEncoder));
        savedMember.drop();

        // when
        ResultActions result = this.mockMvc.perform(
                        delete(BASIC_URL + "/delete/{id}", savedMember.getId())
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                )
                .andExpect(status().isOk());

        // then
        result.andDo(document("member-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("id").description("회원 id")
                )
        ));
    }
}
