package teamproject.lam_server.controller.document.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.annotaiton.WithMockCustomUser;
import teamproject.lam_server.controller.ApiDocsTest;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.constants.Role;
import teamproject.lam_server.domain.member.dto.request.*;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.global.service.SecurityContextFinder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.GENDER_TYPE;
import static teamproject.lam_server.util.CookieUtil.addRefreshTokenCookie;
import static teamproject.lam_server.util.StringUtil.coverContent;
import static teamproject.lam_server.utils.ApiDocumentUtils.*;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateLinkCode;
import static teamproject.lam_server.utils.DocumentFormatGenerator.getDateFormat;

@Transactional
public class MemberApiDocsTest extends ApiDocsTest {
    static final String BASIC_URL = "/api/v1/members";

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SecurityContextFinder finder;

    @Test
    @DisplayName("회원 가입")
    void sign_up() throws Exception {
        MemberCreate request =
                MemberCreate.builder()
                        .loginId("testID")
                        .password("testPassword1!")
                        .name("테스트")
                        .nickname("테스트")
                        .email("testEmail@gmail.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(MemberCreate.class);

        // when
        ResultActions result = this.mockMvc.perform(
                post(BASIC_URL + "/sign-up")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("member-sign-up",
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
    @DisplayName("로그인 아이디 중복 확인")
    void duplicate_check_loginId() throws Exception {
        // given
        MemberCreate request =
                MemberCreate.builder()
                        .loginId("testID")
                        .password("testPassword1!")
                        .name("테스트")
                        .nickname("테스트")
                        .email("testEmail@gmail.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        memberRepository.save(request.toEntity(passwordEncoder));

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/exists/loginId/{login_id}", request.getLoginId())
                                .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").value(false));

        // given
        result.andDo(document("login-id-duplicate-check",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("login_id").description("로그인 아이디")
                ),
                getCheckResponseFieldsSnippet()
        ));
    }

    @Test
    @DisplayName("이메일 중복 확인")
    void duplicate_check_email() throws Exception {
        // given
        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/exists/email/{email}", "test@gmail.com")
                                .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").value(true));


        // given
        result.andDo(document("email-duplicate-check",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("email").description("이메일")
                ),
                getCheckResponseFieldsSnippet()
        ));
    }

    @Test
    @DisplayName("닉네임 중복 확인")
    void duplicate_check_nickname() throws Exception {
        // given
        MemberCreate request =
                MemberCreate.builder()
                        .loginId("testId")
                        .password("testId1!")
                        .name("테스트")
                        .nickname("duplicateNickname")
                        .email("testEmail@gmail.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        Member savedMember = memberRepository.save(request.toEntity(passwordEncoder));

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/exists/nickname/{nickname}", savedMember.getNickname())
                                .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").value(false));

        // given
        result.andDo(document("nickname-duplicate-check",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("nickname").description("닉네임")
                ),
                getCheckResponseFieldsSnippet()
        ));
    }

    @Test
    @DisplayName("아이디 찾기")
    void find_id() throws Exception {
        // given
        MemberCreate create =
                MemberCreate.builder()
                        .loginId("findId")
                        .password("findIdPassword1!")
                        .name("memberName")
                        .nickname("memberNickname")
                        .email("findId@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        memberRepository.save(create.toEntity(passwordEncoder));

        MemberFindId request = new MemberFindId();
        request.setName(create.getName());
        request.setEmail(create.getEmail());

        ConstraintDescriptions constraints = new ConstraintDescriptions(MemberFindId.class);
        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/find-id")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .accept(APPLICATION_JSON)
                                .param("name", request.getName())
                                .param("email", request.getEmail())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..loginId").value(coverContent(create.getLoginId())));

        // then
        result.andDo(document("member-find-id",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParameters(
                        parameterWithName("name")
                                .description("이름")
                                .attributes(getConstraintAttributes(constraints, "name")),
                        parameterWithName("email")
                                .description("이메일")
                                .attributes(getConstraintAttributes(constraints, "email"))
                ),
                customResponseFields("format-response",
                        beneathPath("data").withSubsectionId("data"),
                        attributes(getTitleAttributes("Find LoginID Response Fields")),
                        fieldWithPath("loginId").type(STRING)
                                .description("찾은 아이디"),
                        fieldWithPath("created").type(STRING)
                                .description("생성 날짜")
                                .attributes(getDateFormat("yyyy.MM.dd"))
                )
        ));
    }

    @Test
    @DisplayName("비밀번호 찾기")
    void find_password() throws Exception {
        // given
        MemberCreate create =
                MemberCreate.builder()
                        .loginId("findPassword")
                        .password("findPassword1!")
                        .name("비밀번호찾기")
                        .nickname("비밀번호찾기")
                        .email("findPw@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        memberRepository.save(create.toEntity(passwordEncoder));

        MemberFindPassword request = MemberFindPassword.builder()
                .loginId(create.getLoginId())
                .email(create.getEmail())
                .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(MemberFindPassword.class);

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/find-pw")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("member-find-password",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("loginId").type(STRING)
                                .description("로그인 아이디")
                                .attributes(getConstraintAttributes(constraints, "loginId")),
                        fieldWithPath("email").type(STRING)
                                .description("이메일")
                                .attributes(getConstraintAttributes(constraints, "email"))
                )
        ));
    }

    @Test
    @DisplayName("비밀번호 재확인")
    @WithMockCustomUser(password = "reconfirmPW1!", role = Role.USER)
    void reconfirm_password() throws Exception {
        // given
        MemberReconfirm request = MemberReconfirm.builder()
                .password("reconfirmPW1!")
                .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(MemberReconfirm.class);
        // when
        ResultActions result = this.mockMvc.perform(
                post(BASIC_URL + "/reconfirm")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(this.objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("password-reconfirm",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("password")
                                .description("비밀번호")
                                .attributes(getConstraintAttributes(constraints, "password"))
                ),
                getCheckResponseFieldsSnippet())
        );
    }

    @Test
    @DisplayName("프로필 변경")
    @WithMockCustomUser
    void edit_profile() throws Exception {
        // given
        ProfileEdit request = ProfileEdit.builder()
                .nickname("afterNickname")
                .email("afterEmail@liveamonth.com")
                .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(ProfileEdit.class);

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/profile")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("member-profile-edit",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("nickname").type(STRING)
                                .description("닉네임")
                                .attributes(getConstraintAttributes(constraints, "nickname")),
                        fieldWithPath("email").type(STRING)
                                .description("이메일")
                                .attributes(getConstraintAttributes(constraints, "email"))
                )
        ));
    }


    @Test
    @DisplayName("비밀번호 변경")
    @WithMockCustomUser
    void change_password() throws Exception {
        // given
        PasswordEdit request = PasswordEdit.builder()
                .password("afterPw1!")
                .build();

        ConstraintDescriptions constraints = new ConstraintDescriptions(PasswordEdit.class);

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/password")
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("member-password-change",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                        fieldWithPath("password").type(STRING)
                                .description("변경할 비밀번호")
                                .attributes(getConstraintAttributes(constraints, "password"))
                )
        ));
    }

    @Test
    @DisplayName("회원 탈퇴")
    @WithMockCustomUser
    void drop_member() throws Exception {
        // given
        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/drop")
                        .accept(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("member-drop",
                getDocumentRequest(),
                getDocumentResponse()
        ));
    }

    @Test
    @DisplayName("회원 프로필 조회")
    @WithMockCustomUser
    void get_member_profile() throws Exception {
        // given
        // when
        Member authMember = finder.getLoggedInMember();

        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/profile")
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(authMember.getId()))
                .andExpect(jsonPath("$.data.nickname").value(authMember.getNickname()))
                .andExpect(jsonPath("$.data.email").value(authMember.getEmail()));

        // then
        result.andDo(document("member-profile-get",
                getDocumentRequest(),
                getDocumentResponse(),
                customResponseFields("format-response",
                        beneathPath("data").withSubsectionId("data"),
                        attributes(getTitleAttributes("Member Profile Response Fields")),
                        idFieldWithPath(),
                        fieldWithPath("loginId").type(STRING).description("로그인 아이디"),
                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                        fieldWithPath("image").type(STRING).description("이미지(URL)"),
                        fieldWithPath("name").type(STRING).description("이름"),
                        fieldWithPath("email").type(STRING).description("이메일"),
                        dateFieldWithPath("birth", "생년월일"),
                        enumCodeFieldWithPath("gender", GENDER_TYPE),
                        enumValueFieldWithPath("gender", GENDER_TYPE),
                        fieldWithPath("numberOfReviews").type(NUMBER).description("후기글 수"),
                        fieldWithPath("numberOfSchedules").type(NUMBER).description("스케줄 수"),
                        fieldWithPath("numberOfFollowers").type(NUMBER).description("팔로워 수"),
                        fieldWithPath("numberOfFollows").type(NUMBER).description("팔로우 수")
                )
        ));
    }

    @Test
    @DisplayName("회원 약식 프로필 조회")
    @WithMockCustomUser
    void get_simple_member_profile() throws Exception {
        // given
        // when
        Member authMember = finder.getLoggedInMember();
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/profile/simple")
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                )
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(authMember.getId()))
                .andExpect(jsonPath("$.data.loginId").value(authMember.getLoginId()))
                .andExpect(jsonPath("$.data.nickname").value(authMember.getNickname()));


        // then
        result.andDo(document("member-simple-profile-get",
                getDocumentRequest(),
                getDocumentResponse(),
                customResponseFields("response",
                        beneathPath("data").withSubsectionId("data"),
                        attributes(getTitleAttributes("Member Simple Profile Response Fields")),
                        idFieldWithPath(),
                        fieldWithPath("loginId").type(STRING).description("로그인 아이디"),
                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                        fieldWithPath("image").type(STRING).description("이미지(URL)"),
                        fieldWithPath("numberOfReviews").type(NUMBER).description("후기글 수"),
                        fieldWithPath("numberOfSchedules").type(NUMBER).description("스케줄 수"),
                        fieldWithPath("numberOfFollowers").type(NUMBER).description("팔로워 수"),
                        fieldWithPath("numberOfFollows").type(NUMBER).description("팔로우 수")
                )
        ));
    }

}
