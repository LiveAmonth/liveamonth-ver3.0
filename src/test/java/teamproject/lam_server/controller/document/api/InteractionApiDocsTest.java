package teamproject.lam_server.controller.document.api;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import teamproject.lam_server.annotaiton.WithMockCustomUser;
import teamproject.lam_server.controller.ApiDocsTest;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.domain.interaction.constants.InteractionType;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.member.FollowRepository;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleCommentInteractionRepository;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.ScheduleRepository;
import teamproject.lam_server.global.dto.request.PeriodRequest;
import teamproject.lam_server.global.service.SecurityContextFinder;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.domain.member.constants.Role.USER;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.*;
import static teamproject.lam_server.utils.ApiDocumentUtils.*;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateLinkCode;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class InteractionApiDocsTest extends ApiDocsTest {
    static final String BASIC_URL = "/api/v1/interactions";
    @Autowired
    SecurityContextFinder finder;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleCommentRepository scheduleCommentRepository;
    @Autowired
    private ScheduleCommentInteractionRepository sCInteractionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Member savedMember;

    @BeforeEach
    void set_up_member() {
        MemberCreate memberCreate = MemberCreate.builder()
                .loginId("toMember")
                .nickname("toMember")
                .name("toMember")
                .password("toMember1!")
                .email("toMember@gmail.com")
                .gender(GenderType.MALE)
                .birth(LocalDate.now())
                .build();
        savedMember = memberRepository.save(memberCreate.toEntity(passwordEncoder));
    }

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("팔로우&게시물 상호작용")
    @WithMockCustomUser(loginId = "interaction", role = USER)
    void interact() throws Exception {
        Member authMember = finder.getLoggedInMember();
        // given
        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(savedMember.getId());

        // when
        ResultActions result = this.mockMvc.perform(
                post(BASIC_URL+"/contents/{type}/{login_id}",
                        InteractionType.MEMBER,
                        authMember.getLoginId()
                )
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
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
    @DisplayName("댓글 상호작용")
    @WithMockCustomUser(loginId = "interaction", role = USER)
    void interact_comment() throws Exception {
        Member authMember = finder.getLoggedInMember();
        // given
        ScheduleCreate scheduleCreate = ScheduleCreate.builder()
                .title("테스트 스케줄")
                .city(CityName.SE)
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
                .member(savedMember)
                .schedule(savedSchedule)
                .build();

        ScheduleComment savedComment = scheduleCommentRepository.save(scheduleComment);

        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(savedComment.getId());

        // when
        ResultActions result = this.mockMvc.perform(
                post(
                        BASIC_URL+"/comments/{comment_type}/{login_id}",
                        CommentType.SCHEDULE,
                        authMember.getLoginId()
                )
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
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
    @DisplayName("댓글 상호작용 취소")
    @WithMockCustomUser(loginId = "interaction", role = USER)
    void cancel_interact_comment() throws Exception {
        Member authMember = finder.getLoggedInMember();
        // given
        ScheduleCreate scheduleCreate = ScheduleCreate.builder()
                .title("테스트 스케줄")
                .city(CityName.SE)
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
                .member(savedMember)
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
                        BASIC_URL+"/comments/{comment_type}/{login_id}/cancel",
                        CommentType.SCHEDULE,
                        authMember.getLoginId()
                )
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
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
    @DisplayName("팔로우&게시물 상호작용 여부 조회")
    @WithMockCustomUser(loginId = "interaction", role = USER)
    void is_member_interact_object() throws Exception {
        Member authMember = finder.getLoggedInMember();
        // given
        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(savedMember.getId());

        followRepository.follow(request);

        // when
        ResultActions result = this.mockMvc.perform(
                get(
                        BASIC_URL+"/member/{type}/liked",
                        InteractionType.MEMBER
                )
                        .param("from", String.valueOf(request.getFrom()))
                        .param("to", String.valueOf(request.getTo()))
        ).andExpect(status().isOk());

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
                responseFields(
                        beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("result").type(BOOLEAN).description("결과")
                )
        ));
    }

    @Test
    @DisplayName("회원이 추천&비추천 한 댓글 조회")
    @WithMockCustomUser(loginId = "interaction", role = USER)
    void get_interacted_comments_by_member() throws Exception {
        Member authMember = finder.getLoggedInMember();
        // given
        ScheduleCreate scheduleCreate = ScheduleCreate.builder()
                .title("테스트 스케줄")
                .city(CityName.SE)
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
                .member(savedMember)
                .schedule(savedSchedule)
                .build();

        ScheduleComment scheduleComment2 = ScheduleComment.builder()
                .content("테스트 댓글2")
                .member(savedMember)
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
                        BASIC_URL+"/member/{member_id}/interacted-comments/{comment_type}",
                        authMember.getId(),
                        CommentType.SCHEDULE
                )
                        .params(params)
        ).andExpect(status().isOk());

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
                responseFields(
                        beneathPath("data[]").withSubsectionId("data"),
                        idFieldWithPath(),
                        enumCodeFieldWithPath("state", INTERACTION_STATE),
                        enumValueFieldWithPath("state", INTERACTION_STATE)
                )
        ));
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
