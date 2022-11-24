package teamproject.lam_server.controller.document.api;

import org.junit.jupiter.api.BeforeEach;
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
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.member.FollowRepository;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleEdit;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleRepository;
import teamproject.lam_server.global.dto.request.PeriodRequest;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.PageableDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.global.enumMapper.EnumClassConst.CITY_NAME;
import static teamproject.lam_server.util.CookieUtil.addRefreshTokenCookie;
import static teamproject.lam_server.utils.ApiDocumentUtils.*;
import static teamproject.lam_server.utils.DocsLinkGenerator.generateLinkCode;
import static teamproject.lam_server.utils.DocumentFormatGenerator.getDateFormat;

public class ScheduleApiDocsTest extends ApiDocsTest {

    static final String BASIC_URL = "/api/v1/schedules";
    @Autowired
    SecurityContextFinder finder;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    FollowRepository followRepository;

    @Test
    @Transactional
    @DisplayName("스케줄 추가")
    @WithMockCustomUser
    void add_schedule() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        ScheduleCreate request = ScheduleCreate.builder()
                .title("schedule title")
                .city(CityName.SE.name())
                .period(
                        PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(30))
                                .build()
                )
                .publicFlag(true)
                .build();

        ConstraintDescriptions scheduleCreateCD = new ConstraintDescriptions(ScheduleCreate.class);
        ConstraintDescriptions periodCD = new ConstraintDescriptions(PeriodRequest.class);

        // when
        ResultActions result = this.mockMvc.perform(
                post(BASIC_URL + "/{login_id}", authMember.getLoginId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("schedule-add",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("login_id").description("사용자 아이디")
                ),
                requestFields(
                        titleFieldWithPath()
                                .attributes(getConstraintAttributes(scheduleCreateCD, "title")),
                        fieldWithPath("city").type(STRING)
                                .description(generateLinkCode(CITY_NAME))
                                .attributes(getConstraintAttributes(scheduleCreateCD, "city")),
                        subsectionWithPath("period").type(OBJECT).description("기간")
                                .attributes(getConstraintAttributes(scheduleCreateCD, "period")),
                        fieldWithPath("publicFlag").type(BOOLEAN).description("공개 여부")
                                .attributes(getConstraintAttributes(scheduleCreateCD, "publicFlag")),
                        fieldWithPath("validPeriod").type(BOOLEAN).description("기간 검증 여부")
                                .attributes(getConstraintAttributes(scheduleCreateCD, "validPeriod"))
                ),
                requestFields(
                        beneathPath("period").withSubsectionId("period"),
                        dateFieldWithPath("startDate", "시작 날짜")
                                .attributes(getConstraintAttributes(periodCD, "startDate")),
                        dateFieldWithPath("endDate", "종료 날짜")
                                .attributes(getConstraintAttributes(periodCD, "endDate"))
                ),
                getPostResponseFieldsSnippet()
        ));

    }

    @Test
    @Transactional
    @DisplayName("스케줄 수정")
    @WithMockCustomUser
    void edit_schedule() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();

        ScheduleCreate create = ScheduleCreate.builder()
                .title("schedule title")
                .city(CityName.SE.name())
                .period(
                        PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(30))
                                .build()
                )
                .publicFlag(true)
                .build();

        Schedule savedSchedule = scheduleRepository.save(create.toEntity(authMember));

        ScheduleEdit request = ScheduleEdit.builder()
                .title("title edit")
                .city(CityName.JJ.name())
                .period(
                        PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(20))
                                .build()
                )
                .publicFlag(false)
                .build();

        ConstraintDescriptions scheduleCreateCD = new ConstraintDescriptions(ScheduleEdit.class);
        ConstraintDescriptions periodCD = new ConstraintDescriptions(PeriodRequest.class);

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/{schedule_id}", savedSchedule.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("schedule-edit",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("schedule_id").description("스케줄 id")
                ),
                requestFields(
                        titleFieldWithPath()
                                .attributes(getConstraintAttributes(scheduleCreateCD, "title")),
                        fieldWithPath("city").type(STRING)
                                .description(generateLinkCode(CITY_NAME))
                                .attributes(getConstraintAttributes(scheduleCreateCD, "city")),
                        subsectionWithPath("period").type(OBJECT).description("기간")
                                .attributes(getConstraintAttributes(scheduleCreateCD, "period")),
                        fieldWithPath("publicFlag").type(BOOLEAN).description("공개 여부")
                                .attributes(getConstraintAttributes(scheduleCreateCD, "publicFlag")),
                        fieldWithPath("validPeriod").type(BOOLEAN).description("기간 검증 여부")
                                .attributes(getConstraintAttributes(scheduleCreateCD, "validPeriod"))
                ),
                requestFields(
                        beneathPath("period").withSubsectionId("period"),
                        dateFieldWithPath("startDate", "시작 날짜")
                                .attributes(getConstraintAttributes(periodCD, "startDate")),
                        dateFieldWithPath("endDate", "종료 날짜")
                                .attributes(getConstraintAttributes(periodCD, "endDate"))
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("스케줄 삭제")
    @WithMockCustomUser
    void delete_schedule() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();

        ScheduleCreate create = ScheduleCreate.builder()
                .title("schedule title")
                .city(CityName.SE.name())
                .period(
                        PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(30))
                                .build()
                )
                .publicFlag(true)
                .build();

        Schedule savedSchedule = scheduleRepository.save(create.toEntity(authMember));

        // when
        ResultActions result = this.mockMvc.perform(
                delete(BASIC_URL + "/{schedule_id}", savedSchedule.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("schedule-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("schedule_id").description("스케줄 id")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("스케줄 검색")
    void search_schedule() throws Exception {
        // given
        Member savedMember = createMember();
        createSchedules(savedMember);

        ScheduleSearchCond cond = ScheduleSearchCond.builder()
                .memberNickname(savedMember.getNickname())
                .cityName(CityName.SE)
                .build();

        PageableDTO pageable = PageableDTO.builder().build();

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/search")
                                .accept(APPLICATION_JSON)
                                .param("memberNickname", cond.getMemberNickname())
                                .param("cityName", cond.getCityName().getCode())
                                .param("title", cond.getTitle())
                                .param("startDate", cond.getStartDate() != null ? String.format(cond.getStartDate().toString(), "yyyy-MM-dd") : "")
                                .param("page", pageable.getPage().toString())
                                .param("size", pageable.getSize().toString())
                                .param("sorts", "id,desc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..city[?(@.code == '%s')]", CityName.SE.name()).exists())
                .andExpect(jsonPath("$..profile[?(@.nickname == '%s')]", savedMember.getNickname()).exists());

        // then
        result.andDo(document("schedule-search",
                getDocumentRequest(),
                getDocumentResponse(),
                requestParameters(
                        parameterWithName("memberNickname").description("작성자 닉네임").optional(),
                        parameterWithName("title").description("스케줄 제목").optional(),
                        parameterWithName("cityName").description(generateLinkCode(CITY_NAME)).optional(),
                        parameterWithName("startDate").description("시작 날짜").attributes(getDateFormat()),
                        parameterWithName("page").description("페이지 번호"),
                        parameterWithName("size").description("컨텐츠 수"),
                        parameterWithName("sorts").description("정렬 옵션")
                ),
                customResponseFields("response",
                        beneathPath("data.content[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Schedule Card Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        enumCodeFieldWithPath("city", CITY_NAME),
                        enumValueFieldWithPath("city", CITY_NAME),
                        subsectionWithPath("profile").type(OBJECT).description("작성자 프로필"),
                        fieldWithPath("cost").type(NUMBER).description("총 비용"),
                        fieldWithPath("numberOfHits").type(NUMBER).description("조회수"),
                        fieldWithPath("numberOfLikes").type(NUMBER).description("좋아요수"),
                        fieldWithPath("numberOfComments").type(NUMBER).description("댓글수"),
                        subsectionWithPath("period").type(OBJECT).description("기간"),
                        fieldWithPath("publicFlag").type(BOOLEAN).description("공개 여부")
                ),
                customResponseFields("response",
                        beneathPath("data.content[].profile").withSubsectionId("profile"),
                        attributes(getTitleAttributes("Member Profile Response Fields")),
                        idFieldWithPath(),
                        fieldWithPath("loginId").type(STRING).description("로그인 아이디"),
                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                        fieldWithPath("image").type(STRING).description("프로필 이미지(URL)"),
                        fieldWithPath("numberOfReviews").type(NUMBER).description("후기글 갯수"),
                        fieldWithPath("numberOfSchedules").type(NUMBER).description("스케줄 갯수"),
                        fieldWithPath("numberOfFollowers").type(NUMBER).description("팔로워 갯수"),
                        fieldWithPath("numberOfFollows").type(NUMBER).description("팔로우 갯수")
                ),
                customResponseFields("format-response",
                        beneathPath("data.content[].period").withSubsectionId("period"),
                        attributes(getTitleAttributes("Date Period Response Fields")),
                        dateFieldWithPath("startDate", "시작 기간"),
                        dateFieldWithPath("endDate", "종료 기간")
                )
        ));

    }

    @Test
    @Transactional
    @DisplayName("수정할 스케줄 다건 조회")
    @WithMockCustomUser
    void get_my_schedules_to_edit() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();

        List<Schedule> mySchedules = createSchedules(authMember);

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/list/{login_id}/edit", authMember.getLoginId())
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..[?(@.id == '%s')]", mySchedules.get(0).getId()).exists())
                .andExpect(jsonPath("$..[?(@.id == '%s')]", mySchedules.get(1).getId()).exists())
                .andExpect(jsonPath("$..[?(@.id == '%s')]", mySchedules.get(2).getId()).exists());

        // then
        result.andDo(document("my-schedule-list-edit",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("login_id").description("사용자 아이디")
                ),
                customResponseFields("response",
                        beneathPath("data[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Editable Schedule Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        enumCodeFieldWithPath("city", CITY_NAME),
                        enumValueFieldWithPath("city", CITY_NAME),
                        subsectionWithPath("period").type(OBJECT).description("기간"),
                        fieldWithPath("publicFlag").type(BOOLEAN).description("공개 여부")
                ),
                customResponseFields("format-response",
                        beneathPath("data[].period").withSubsectionId("period"),
                        attributes(getTitleAttributes("Date Period Response Fields")),
                        dateFieldWithPath("startDate", "시작 기간"),
                        dateFieldWithPath("endDate", "종료 기간")
                )
        ));
    }


    @Test
    @Transactional
    @DisplayName("내 스케줄 다건 조회")
    @WithMockCustomUser
    void get_my_schedule_list() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        List<Schedule> mySchedules = createSchedules(authMember);

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/list/{login_id}", authMember.getLoginId())
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..[?(@.id == '%s')]", mySchedules.get(1).getId()).exists())
                .andExpect(jsonPath("$..[?(@.id == '%s')]", mySchedules.get(2).getId()).exists());

        // then
        result.andDo(document("my-schedule-list-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("login_id").description("사용자 아이디")
                ),
                requestParameters(
                        parameterWithName("size").description("사이즈").optional(),
                        parameterWithName("last_id").description("마지막 id").optional()
                ),
                customResponseFields("response",
                        beneathPath("data[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("My Schedule Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        enumCodeFieldWithPath("city", CITY_NAME),
                        enumValueFieldWithPath("city", CITY_NAME),
                        subsectionWithPath("period").type(OBJECT).description("기간"),
                        fieldWithPath("publicFlag").type(BOOLEAN).description("공개 여부"),
                        fieldWithPath("numberOfHits").type(NUMBER).description("조회수"),
                        fieldWithPath("numberOfLikes").type(NUMBER).description("좋아요수"),
                        fieldWithPath("numberOfComments").type(NUMBER).description("댓글수")
                ),
                customResponseFields("format-response",
                        beneathPath("data[].period").withSubsectionId("period"),
                        attributes(getTitleAttributes("Date Period Response Fields")),
                        dateFieldWithPath("startDate", "시작 기간"),
                        dateFieldWithPath("endDate", "종료 기간")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("팔로우 스케줄 다건 조회")
    @WithMockCustomUser
    void search_followed_schedule_list() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        Member followMember = createMember();
        List<Schedule> schedules = createSchedules(followMember);
        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(followMember.getId());
        followRepository.follow(request);

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/list/{login_id}/followed", authMember.getLoginId())
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..[?(@.id == '%s')]", schedules.get(1).getId()).exists())
                .andExpect(jsonPath("$..[?(@.id == '%s')]", schedules.get(2).getId()).exists());

        // then
        result.andDo(document("followed-schedule-list-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("login_id").description("사용자 아이디")
                ),
                requestParameters(
                        parameterWithName("size").description("사이즈").optional(),
                        parameterWithName("last_id").description("마지막 id").optional()
                ),
                customResponseFields("response",
                        beneathPath("data[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Schedule Card Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        enumCodeFieldWithPath("city", CITY_NAME),
                        enumValueFieldWithPath("city", CITY_NAME),
                        subsectionWithPath("profile").type(OBJECT).description("작성자 프로필"),
                        fieldWithPath("cost").type(NUMBER).description("총 비용"),
                        fieldWithPath("numberOfHits").type(NUMBER).description("조회수"),
                        fieldWithPath("numberOfLikes").type(NUMBER).description("좋아요수"),
                        fieldWithPath("numberOfComments").type(NUMBER).description("댓글수"),
                        subsectionWithPath("period").type(OBJECT).description("기간"),
                        fieldWithPath("publicFlag").type(BOOLEAN).description("공개 여부")
                ),
                customResponseFields("response",
                        beneathPath("data[].profile").withSubsectionId("profile"),
                        attributes(getTitleAttributes("Member Profile Response Fields")),
                        idFieldWithPath(),
                        fieldWithPath("loginId").type(STRING).description("로그인 아이디"),
                        fieldWithPath("nickname").type(STRING).description("닉네임"),
                        fieldWithPath("image").type(STRING).description("프로필 이미지(URL)"),
                        fieldWithPath("numberOfReviews").type(NUMBER).description("후기글 갯수"),
                        fieldWithPath("numberOfSchedules").type(NUMBER).description("스케줄 갯수"),
                        fieldWithPath("numberOfFollowers").type(NUMBER).description("팔로워 갯수"),
                        fieldWithPath("numberOfFollows").type(NUMBER).description("팔로우 갯수")
                ),
                customResponseFields("format-response",
                        beneathPath("data[].period").withSubsectionId("period"),
                        attributes(getTitleAttributes("Date Period Response Fields")),
                        dateFieldWithPath("startDate", "시작 기간"),
                        dateFieldWithPath("endDate", "종료 기간")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("팔로우 스케줄 모든 갯수 조회")
    @WithMockCustomUser
    void get_followed_schedule_count() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        Member followMember = createMember();
        List<Schedule> schedules = createSchedules(followMember);
        InteractionRequest request = new InteractionRequest();
        request.setFrom(authMember.getId());
        request.setTo(followMember.getId());
        followRepository.follow(request);

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/count/{login_id}/followed", authMember.getLoginId())
                                .accept(APPLICATION_JSON)
                                .header("Authorization", "{access_token}")
                                .cookie(addRefreshTokenCookie("{refresh_token}"))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.counts", is(schedules.size())));

        // then
        result.andDo(document("followed-schedule-count-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("login_id").description("사용자 아이디")
                ),
                customResponseFields(
                        "response",
                        beneathPath("data").withSubsectionId("data"),
                        attributes(getTitleAttributes("Count response Fields")),
                        fieldWithPath("counts").type(NUMBER).description("갯수")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("스케줄 조회수 증가")
    void schedule_view_count_up() throws Exception {
        // given
        List<Schedule> schedules = createSchedules(createMember());

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/{schedule_id}/count-up", schedules.get(0).getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
        ).andExpect(status().isOk());

        // then
        result.andDo(document("schedule-view-count-up",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("schedule_id").description("스케줄 id")
                )
        ));
    }

    Member createMember() {
        MemberCreate memberCreate =
                MemberCreate.builder()
                        .loginId("member")
                        .password("memberPassword1!")
                        .name("memberName")
                        .nickname("memberNickname")
                        .email("member@liveamonth.com")
                        .birth(LocalDate.now().minusDays(1))
                        .gender(GenderType.MALE.name())
                        .build();
        return memberRepository.save(memberCreate.toEntity(passwordEncoder));
    }

    List<Schedule> createSchedules(Member member) {
        List<ScheduleCreate> creates = List.of(
                ScheduleCreate.builder()
                        .title("my schedule one")
                        .city(CityName.SE.name())
                        .period(
                                PeriodRequest.builder()
                                        .startDate(LocalDate.now())
                                        .endDate(LocalDate.now().plusDays(30))
                                        .build()
                        )
                        .publicFlag(true)
                        .build(),
                ScheduleCreate.builder()
                        .title("my schedule two")
                        .city(CityName.SE.name())
                        .period(
                                PeriodRequest.builder()
                                        .startDate(LocalDate.now().plusDays(10))
                                        .endDate(LocalDate.now().plusDays(30))
                                        .build()
                        )
                        .publicFlag(true)
                        .build(),
                ScheduleCreate.builder()
                        .title("my schedule three")
                        .city(CityName.JJ.name())
                        .period(
                                PeriodRequest.builder()
                                        .startDate(LocalDate.now())
                                        .endDate(LocalDate.now().plusDays(30))
                                        .build()
                        )
                        .publicFlag(true)
                        .build());
        return scheduleRepository.saveAll(
                creates.stream().
                        map(scheduleCreate -> scheduleCreate.toEntity(member))
                        .collect(Collectors.toList()));
    }


}
