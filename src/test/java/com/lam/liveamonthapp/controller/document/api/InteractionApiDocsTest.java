package com.lam.liveamonthapp.controller.document.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.lam.liveamonthapp.annotaiton.WithMockCustomUser;
import com.lam.liveamonthapp.controller.ApiDocsTest;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.comment.constants.CommentType;
import com.lam.liveamonthapp.domain.comment.entity.ScheduleComment;
import com.lam.liveamonthapp.domain.comment.repository.ScheduleCommentRepository;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionState;
import com.lam.liveamonthapp.domain.interaction.constants.InteractionType;
import com.lam.liveamonthapp.domain.interaction.dto.InteractionRequest;
import com.lam.liveamonthapp.domain.interaction.repository.member.FollowRepository;
import com.lam.liveamonthapp.domain.interaction.repository.schedule.ScheduleCommentInteractionRepository;
import com.lam.liveamonthapp.domain.member.constants.GenderType;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.domain.schedule.dto.request.ScheduleCreate;
import com.lam.liveamonthapp.domain.schedule.entity.Schedule;
import com.lam.liveamonthapp.domain.schedule.repository.core.ScheduleRepository;
import com.lam.liveamonthapp.global.dto.request.PeriodRequest;
import com.lam.liveamonthapp.global.service.SecurityContextFinder;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.lam.liveamonthapp.global.enumMapper.EnumClassConst.*;
import static com.lam.liveamonthapp.util.CookieUtil.addRefreshTokenCookie;
import static com.lam.liveamonthapp.utils.ApiDocumentUtils.*;
import static com.lam.liveamonthapp.utils.DocsLinkGenerator.generateLinkCode;

public class InteractionApiDocsTest extends ApiDocsTest {
    static final String BASIC_URL = "/api/v1/interactions";
    @Autowired
    SecurityContextFinder finder;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ScheduleCommentRepository scheduleCommentRepository;
    @Autowired
    ScheduleCommentInteractionRepository sCInteractionRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Transactional
    @DisplayName("팔로우&게시물 상호작용")
    @WithMockCustomUser
    void interact() throws Exception {
        Member authMember = finder.getLoggedInMember();
        Member toMember = createMember();

        // given
        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(toMember.getId());

        // when
        ResultActions result = this.mockMvc.perform(
                post(BASIC_URL + "/contents/{type}/{login_id}",
                        InteractionType.MEMBER,
                        authMember.getLoginId()
                )
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(this.objectMapper.writeValueAsString(request))
                        .param("is_interacted", String.valueOf(false))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("interact-object",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("type").description(generateLinkCode(INTERACTION_TYPE)),
                        parameterWithName("login_id").description("로그인 회원 아이디")
                ),
                requestParameters(
                        parameterWithName("is_interacted").description("상호작용 여부")
                ),
                getInteractionRequestSnippet("받는 객체 id")
        ));
    }

    @Test
    @Transactional
    @DisplayName("댓글 상호작용")
    @WithMockCustomUser
    void interact_comment() throws Exception {
        Member toMember = createMember();
        Member authMember = finder.getLoggedInMember();
        // given
        ScheduleCreate scheduleCreate = ScheduleCreate.builder()
                .title("테스트 스케줄")
                .city(CityName.SE.name())
                .period(
                        PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(1))
                                .build()
                )
                .publicFlag(true)
                .build();

        Schedule savedSchedule = scheduleRepository.save(scheduleCreate.toEntity(authMember));

        ScheduleComment scheduleComment = ScheduleComment.builder()
                .content("테스트 댓글")
                .member(toMember)
                .schedule(savedSchedule)
                .build();

        ScheduleComment savedComment = scheduleCommentRepository.save(scheduleComment);

        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(savedComment.getId());

        // when
        ResultActions result = this.mockMvc.perform(
                post(
                        BASIC_URL + "/comments/{comment_type}/{login_id}",
                        CommentType.SCHEDULE,
                        authMember.getLoginId()
                )
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(this.objectMapper.writeValueAsString(request))
                        .param("interaction_state", InteractionState.LIKE.name())
        ).andExpect(status().isOk());

        // then
        result.andDo(document("interact-comment",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("comment_type").description(generateLinkCode(COMMENT_TYPE)),
                        parameterWithName("login_id").description("로그인 회원 아이디")
                ),
                requestParameters(
                        parameterWithName("interaction_state").description(generateLinkCode(INTERACTION_STATE))
                ),
                getInteractionRequestSnippet("받는 객체 id")
        ));
    }

    @Test
    @Transactional
    @DisplayName("댓글 상호작용 취소")
    @WithMockCustomUser
    void cancel_interact_comment() throws Exception {
        // given
        Member toMember = createMember();
        Member authMember = finder.getLoggedInMember();

        ScheduleCreate scheduleCreate = ScheduleCreate.builder()
                .title("테스트 스케줄")
                .city(CityName.SE.name())
                .period(
                        PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(1))
                                .build()
                )
                .publicFlag(true)
                .build();

        Schedule savedSchedule = scheduleRepository.save(scheduleCreate.toEntity(authMember));

        ScheduleComment scheduleComment = ScheduleComment.builder()
                .content("테스트 댓글")
                .member(toMember)
                .schedule(savedSchedule)
                .build();

        ScheduleComment savedComment = scheduleCommentRepository.save(scheduleComment);

        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(savedComment.getId());

        sCInteractionRepository.interact(request, InteractionState.LIKE);

        // when
        ResultActions result = this.mockMvc.perform(
                post(
                        BASIC_URL + "/comments/{comment_type}/{login_id}/cancel",
                        CommentType.SCHEDULE,
                        authMember.getLoginId()
                )
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(this.objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("interact-comment-cancel",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("comment_type").description(generateLinkCode(COMMENT_TYPE)),
                        parameterWithName("login_id").description("로그인 회원 아이디")
                ),
                getInteractionRequestSnippet("취소할 객체 id")
        ));
    }

    @Test
    @Transactional
    @DisplayName("팔로우&게시물 상호작용 여부 조회")
    @WithMockCustomUser
    void is_member_interact_object() throws Exception {
        // given
        Member toMember = createMember();
        Member authMember = finder.getLoggedInMember();

        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(toMember.getId());

        followRepository.follow(request);

        // when
        ResultActions result = this.mockMvc.perform(
                        get(
                                BASIC_URL + "/member/{type}/liked",
                                InteractionType.MEMBER
                        )
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                                .param("from", String.valueOf(request.getFrom()))
                                .param("to", String.valueOf(request.getTo()))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.result").value(true));

        // then
        result.andDo(document("is-member-interact-object",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("type").description(generateLinkCode(INTERACTION_TYPE))
                ),
                requestParameters(
                        parameterWithName("from").description("보낸 회원 id"),
                        parameterWithName("to").description("확인할 객체 id")
                ),
                getCheckResponseFieldsSnippet()
        ));
    }

    @Test
    @Transactional
    @DisplayName("회원이 추천&비추천 한 댓글 조회")
    @WithMockCustomUser
    void get_interacted_comments_by_member() throws Exception {
        // given
        Member toMember = createMember();
        Member authMember = finder.getLoggedInMember();

        ScheduleCreate scheduleCreate = ScheduleCreate.builder()
                .title("테스트 스케줄")
                .city(CityName.SE.name())
                .period(
                        PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(1))
                                .build()
                )
                .publicFlag(true)
                .build();

        Schedule savedSchedule = scheduleRepository.save(scheduleCreate.toEntity(authMember));
        ScheduleComment scheduleComment1 = ScheduleComment.builder()
                .content("테스트 댓글1")
                .member(toMember)
                .schedule(savedSchedule)
                .build();

        ScheduleComment scheduleComment2 = ScheduleComment.builder()
                .content("테스트 댓글2")
                .member(toMember)
                .schedule(savedSchedule)
                .build();

        ScheduleComment savedComment1 = scheduleCommentRepository.save(scheduleComment1);
        ScheduleComment savedComment2 = scheduleCommentRepository.save(scheduleComment2);

        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(savedComment1.getId());
        sCInteractionRepository.interact(request, InteractionState.LIKE);

        request.setTo(savedComment2.getId());
        sCInteractionRepository.interact(request, InteractionState.LIKE);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.addAll("ids", List.of(String.valueOf(savedComment1.getId()), String.valueOf(savedComment2.getId())));

        // when
        ResultActions result = this.mockMvc.perform(
                        get(
                                BASIC_URL + "/member/{member_id}/interacted-comments/{comment_type}",
                                authMember.getId(),
                                CommentType.SCHEDULE
                        )
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                                .params(params)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(2)))
                .andExpect(jsonPath("$..[?(@.id == '%d')]", savedComment1.getId()).exists())
                .andExpect(jsonPath("$..state[?(@.code == '%s')]", InteractionState.LIKE.name()).exists());

        // then
        result.andDo(document("member-interacted-comments-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("member_id").description("찾을 회원 id"),
                        parameterWithName("comment_type").description(generateLinkCode(COMMENT_TYPE))
                ),
                requestParameters(
                        parameterWithName("ids").description("확인할 댓글 id 리스트")
                ),
                customResponseFields("response",
                        beneathPath("data[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Interacted Comment Response Fields")),
                        idFieldWithPath(),
                        enumCodeFieldWithPath("state", INTERACTION_STATE),
                        enumValueFieldWithPath("state", INTERACTION_STATE)
                )
        ));
    }

    private Member createMember() {
        MemberCreate memberCreate = MemberCreate.builder()
                .loginId("toMember")
                .nickname("toMember")
                .name("toMember")
                .password("toMember1!")
                .email("toMember@gmail.com")
                .gender(GenderType.MALE.name())
                .birth(LocalDate.now().minusDays(1))
                .build();
        return memberRepository.save(memberCreate.toEntity(passwordEncoder));
    }

    private RequestFieldsSnippet getInteractionRequestSnippet(String to) {
        ConstraintDescriptions constraints = new ConstraintDescriptions(InteractionRequest.class);
        return requestFields(
                fieldWithPath("from")
                        .description("보내는 회원 id")
                        .attributes(getConstraintAttributes(constraints, "from")),
                fieldWithPath("to")
                        .description(to)
                        .attributes(getConstraintAttributes(constraints, "to"))
        );
    }
}
