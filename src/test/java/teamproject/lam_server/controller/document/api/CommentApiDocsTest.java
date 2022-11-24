package teamproject.lam_server.controller.document.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.annotaiton.WithMockCustomUser;
import teamproject.lam_server.controller.ApiDocsTest;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.comment.constants.CommentType;
import teamproject.lam_server.domain.comment.dto.request.CommentCreate;
import teamproject.lam_server.domain.comment.dto.request.CommentEdit;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;
import teamproject.lam_server.domain.comment.repository.ScheduleCommentRepository;
import teamproject.lam_server.domain.interaction.constants.InteractionState;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.schedule.ScheduleCommentInteractionRepository;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleRepository;
import teamproject.lam_server.exception.notfound.CommentNotFound;
import teamproject.lam_server.global.dto.request.PeriodRequest;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.PageableDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.COMMENT_TYPE;
import static teamproject.lam_server.util.CookieUtil.addRefreshTokenCookie;
import static teamproject.lam_server.utils.ApiDocumentUtils.*;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateLinkCode;

@Slf4j
public class CommentApiDocsTest extends ApiDocsTest {

    static final String BASIC_URL = "/api/v1/comments";
    @Autowired
    SecurityContextFinder finder;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ScheduleCommentInteractionRepository interactionRepository;
    @Autowired
    ScheduleCommentRepository commentRepository;

    @Test
    @Transactional
    @DisplayName("댓글 작성")
    @WithMockCustomUser
    void write_comment() throws Exception {
        // given
        Member member = createMember();
        Schedule schedule = createSchedule(member);

        CommentCreate request = CommentCreate.builder()
                .comment("comment")
                .build();

        ConstraintDescriptions constraint = new ConstraintDescriptions(CommentCreate.class);

        // when
        ResultActions result = this.mockMvc.perform(
                post(BASIC_URL + "/{type}/{content_id}",
                        CommentType.SCHEDULE.name(),
                        schedule.getId()
                )
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("comment-write",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("type").description(generateLinkCode(COMMENT_TYPE)),
                        parameterWithName("content_id").description("게시물 id")
                ),
                requestFields(
                        fieldWithPath("comment").type(STRING).description("댓글")
                                .attributes(getConstraintAttributes(constraint, "comment")),
                        fieldWithPath("parentId").type(NUMBER).description("댓글 id(대댓글 작성)")
                                .attributes(getConstraintAttributes(constraint, "parentId"))
                                .optional()
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("댓글 삭제")
    @WithMockCustomUser
    void delete_comment() throws Exception {
        // given
        Member member = createMember();
        Schedule schedule = createSchedule(member);
        Member authMember = finder.getLoggedInMember();
        CommentCreate create = CommentCreate.builder()
                .comment("comment")
                .build();
        ScheduleComment savedComment = commentRepository.save(create.toScheduleEntity(authMember, schedule));

        // when
        ResultActions result = this.mockMvc.perform(
                delete(BASIC_URL + "/{type}/{comment_id}",
                        CommentType.SCHEDULE.name(),
                        savedComment.getId()
                )
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("comment-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("type").description(generateLinkCode(COMMENT_TYPE)),
                        parameterWithName("comment_id").description("댓글 id")
                )
        ));
    }

    @Test
    @DisplayName("댓글 수정")
    @Transactional
    @WithMockCustomUser
    void edit_comment() throws Exception {
        // given
        Member member = createMember();
        Schedule schedule = createSchedule(member);
        Member authMember = finder.getLoggedInMember();
        CommentCreate create = CommentCreate.builder()
                .comment("comment")
                .build();
        ScheduleComment savedComment = commentRepository.save(create.toScheduleEntity(authMember, schedule));

        CommentEdit request = CommentEdit.builder()
                .comment("comment edit")
                .build();

        ConstraintDescriptions constraint = new ConstraintDescriptions(CommentEdit.class);

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/{type}/{comment_id}",
                        CommentType.SCHEDULE.name(),
                        savedComment.getId()
                )
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("comment-edit",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("type").description(generateLinkCode(COMMENT_TYPE)),
                        parameterWithName("comment_id").description("댓글 id")
                ),
                requestFields(
                        fieldWithPath("comment").type(STRING).description("댓글")
                                .attributes(getConstraintAttributes(constraint, "comment"))
                )
        ));
    }


    @Test
    @Transactional
    @DisplayName("댓글 다건 조회")
    void get_comment_list() throws Exception {
        // given
        Member member = createMember();
        Schedule schedule = createSchedule(member);
        PageableDTO pageable = PageableDTO.builder().build();
        List<ScheduleComment> comments = createComments(schedule, member);
        createCommentReplies(member, comments);

        // when
        ResultActions result = this.mockMvc.perform(
                get(BASIC_URL + "/{type}/{content_id}",
                        CommentType.SCHEDULE.name(),
                        schedule.getId()
                )
                        .accept(APPLICATION_JSON)
                        .param("page", pageable.getPage().toString())
                        .param("size", pageable.getSize().toString())
                        .param("sorts", "id,desc")
        ).andExpect(status().isOk());

        // then
        result.andDo(document("comment-list-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("type").description(generateLinkCode(COMMENT_TYPE)),
                        parameterWithName("content_id").description("게시물 id")
                ),
                requestParameters(
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("컨텐츠 수"),
                        parameterWithName("sorts").description("정렬 옵션")
                ),
                customResponseFields("response",
                        beneathPath("data.content[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Comment Response Fields")),
                        fieldWithPath("commentId").type(NUMBER).description("댓글 id"),
                        fieldWithPath("comment").type(STRING).description("댓글 내용"),
                        subsectionWithPath("profile").type(OBJECT).description("작성자 프로필"),
                        fieldWithPath("elapsedTime").type(STRING).description("작성 경과 시간"),
                        fieldWithPath("numberOfLikes").type(NUMBER).description("추천 수"),
                        fieldWithPath("numberOfDislikes").type(NUMBER).description("비추천 수"),
                        subsectionWithPath("commentReplies[]").type(ARRAY).description("대댓글 리스트")
                ),
                customResponseFields("response",
                        beneathPath("data.content[].profile").withSubsectionId("profile"),
                        attributes(getTitleAttributes("Member Comment Profile Response Fields")),
                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                        fieldWithPath("image").type(STRING).description("프로필 이미지(URL)")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("베스트 댓글 다건 조회")
    void get_best_comment_list() throws Exception {
        // given
        Member member = createMember();
        Schedule schedule = createSchedule(member);
        List<ScheduleComment> comments = createComments(schedule, member);
        List<Member> members = createMembers();
        InteractionRequest request = new InteractionRequest();
        for (int i = 0; i < comments.size(); i++) {
            request.setTo(comments.get(i).getId());
            for (int j = 0; j < 3 - i; j++) {
                request.setFrom(members.get(j).getId());
                interactionRepository.interact(request, InteractionState.LIKE);
            }
        }

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/{type}/{content_id}/best",
                                CommentType.SCHEDULE.name(),
                                schedule.getId()
                        )
                                .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        // then
        result.andDo(document("best-comment-list-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("type").description(generateLinkCode(COMMENT_TYPE)),
                        parameterWithName("content_id").description("게시물 id")
                ),
                customResponseFields("response",
                        beneathPath("data[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Best Comment Response Fields")),
                        fieldWithPath("commentId").type(NUMBER).description("댓글 id"),
                        fieldWithPath("comment").type(STRING).description("댓글 내용"),
                        subsectionWithPath("profile").type(OBJECT).description("작성자 프로필"),
                        fieldWithPath("elapsedTime").type(STRING).description("작성 경과 시간"),
                        fieldWithPath("numberOfLikes").type(NUMBER).description("추천 수"),
                        fieldWithPath("numberOfDislikes").type(NUMBER).description("비추천 수")
                ),
                customResponseFields("response",
                        beneathPath("data[].profile").withSubsectionId("profile"),
                        attributes(getTitleAttributes("Member Comment Profile Response Fields")),
                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                        fieldWithPath("image").type(STRING).description("프로필 이미지(URL)")
                )
        ));
    }

    List<ScheduleComment> createComments(Schedule schedule, Member member) {
        List<CommentCreate> creates = List.of(
                CommentCreate.builder()
                        .comment("comment one")
                        .build(),
                CommentCreate.builder()
                        .comment("comment two")
                        .build(),
                CommentCreate.builder()
                        .comment("comment three")
                        .build()
        );
        return commentRepository.saveAll(
                creates.stream()
                        .map(commentCreate -> commentCreate.toScheduleEntity(member, schedule))
                        .collect(Collectors.toList()));
    }

    void createCommentReplies(Member member, List<ScheduleComment> parents) {
        Schedule schedule = createSchedule(member);

        List<CommentCreate> creates = List.of(
                CommentCreate.builder()
                        .comment("comment one")
                        .parentId(parents.get(0).getId())
                        .build(),
                CommentCreate.builder()
                        .comment("comment two")
                        .parentId(parents.get(1).getId())
                        .build(),
                CommentCreate.builder()
                        .comment("comment three")
                        .parentId(parents.get(2).getId())
                        .build()
        );
        commentRepository.saveAll(
                creates.stream()
                        .map(commentCreate -> commentCreate.toScheduleEntity(member, schedule, commentRepository.findById(commentCreate.getParentId()).orElseThrow(CommentNotFound::new)))
                        .collect(Collectors.toList()));
    }

    Schedule createSchedule(Member member) {
        ScheduleCreate scheduleCreate = ScheduleCreate.builder()
                .title("defaultSchedule")
                .city(CityName.SE.name())
                .period(
                        PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(30))
                                .build()
                )
                .publicFlag(true)
                .build();
        return scheduleRepository.save(scheduleCreate.toEntity(member));
    }

    private Member createMember() {
        MemberCreate memberCreate = MemberCreate.builder()
                .loginId("defaultMember")
                .nickname("defaultMember")
                .name("defaultMember")
                .password("defaultMember1!")
                .email("defaultMember@liveamonth.com")
                .gender(GenderType.MALE.name())
                .birth(LocalDate.now().minusDays(1))
                .build();
        return memberRepository.save(memberCreate.toEntity(passwordEncoder));
    }

    List<Member> createMembers() {
        List<MemberCreate> memberCreates = List.of(
                MemberCreate.builder()
                        .loginId("member1")
                        .password("memberPassword1!")
                        .name("memberName1")
                        .nickname("memberNickname1")
                        .email("member1@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build(),
                MemberCreate.builder()
                        .loginId("member2")
                        .password("memberPassword1!")
                        .name("memberName2")
                        .nickname("memberNickname2")
                        .email("member2@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build(),
                MemberCreate.builder()
                        .loginId("member3")
                        .password("memberPassword1!")
                        .name("memberName3")
                        .nickname("memberNickname3")
                        .email("member3@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build()
        );
        return memberRepository.saveAll(memberCreates.stream()
                .map(create -> create.toEntity(passwordEncoder))
                .collect(Collectors.toList()));
    }
}
