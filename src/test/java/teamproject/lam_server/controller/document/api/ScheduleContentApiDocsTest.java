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
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentCreate;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleContentEdit;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.entity.ScheduleContent;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleContentRepository;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleRepository;
import teamproject.lam_server.global.dto.request.PeriodRequest;
import teamproject.lam_server.global.dto.request.TimePeriodRequest;
import teamproject.lam_server.global.service.SecurityContextFinder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static teamproject.lam_server.util.CookieUtil.addRefreshTokenCookie;
import static teamproject.lam_server.utils.ApiDocumentUtils.*;

public class ScheduleContentApiDocsTest extends ApiDocsTest {

    static final String BASIC_URL = "/api/v1/schedules";

    @Autowired
    SecurityContextFinder finder;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    ScheduleContentRepository scheduleContentRepository;

    @Test
    @Transactional
    @DisplayName("스케줄 컨텐츠 추가")
    @WithMockCustomUser
    void add_schedule_content() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        Schedule schedule = createSchedule(authMember);

        ScheduleContentCreate request = ScheduleContentCreate.builder()
                .title("content title")
                .content("content")
                .timePeriod(
                        TimePeriodRequest.builder()
                                .startDateTime(getDateTime(true, schedule, 6))
                                .endDateTime(getDateTime(false, schedule, 6))
                                .build()
                )
                .cost(10000)
                .build();

        ConstraintDescriptions contentCreateCD = new ConstraintDescriptions(ScheduleContentCreate.class);
        ConstraintDescriptions timePeriodCD = new ConstraintDescriptions(TimePeriodRequest.class);

        // when
        ResultActions result = this.mockMvc.perform(
                post(BASIC_URL + "/{schedule_id}/contents", schedule.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("schedule-content-add",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("schedule_id").description("스케줄 id")
                ),
                requestFields(
                        titleFieldWithPath()
                                .attributes(getConstraintAttributes(contentCreateCD, "title")),
                        fieldWithPath("content").type(STRING).description("내용")
                                .attributes(getConstraintAttributes(contentCreateCD, "content")),
                        subsectionWithPath("timePeriod").type(OBJECT).description("기간")
                                .attributes(getConstraintAttributes(contentCreateCD, "timePeriod")),
                        fieldWithPath("cost").type(NUMBER).description("비용")
                                .attributes(getConstraintAttributes(contentCreateCD, "cost")),
                        fieldWithPath("validTimePeriod").type(BOOLEAN).description("기간 검증 여부")
                                .attributes(getConstraintAttributes(contentCreateCD, "validTimePeriod"))
                ),
                requestFields(
                        beneathPath("timePeriod").withSubsectionId("time-period"),
                        dateTimeFieldWithPath("startDateTime", "시작 시간")
                                .attributes(getConstraintAttributes(timePeriodCD, "startDateTime")),
                        dateTimeFieldWithPath("endDateTime", "종료 시간")
                                .attributes(getConstraintAttributes(timePeriodCD, "endDateTime"))
                ),
                getPostResponseFieldsSnippet()
        ));
    }

    @Test
    @Transactional
    @DisplayName("스케줄 컨텐츠 수정")
    @WithMockCustomUser
    void edit_schedule_content() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        Schedule schedule = createSchedule(authMember);
        ScheduleContent content = createContent(schedule);

        ScheduleContentEdit request = ScheduleContentEdit.builder()
                .title("content title")
                .content("content")
                .timePeriod(
                        TimePeriodRequest.builder()
                                .startDateTime(getDateTime(true, schedule, 6))
                                .endDateTime(getDateTime(false, schedule, 6))
                                .build()
                )
                .cost(10000)
                .build();

        ConstraintDescriptions contentEditCD = new ConstraintDescriptions(ScheduleContentEdit.class);
        ConstraintDescriptions timePeriodCD = new ConstraintDescriptions(TimePeriodRequest.class);

        // when
        ResultActions result = this.mockMvc.perform(
                patch(BASIC_URL + "/contents/{content_id}", content.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("schedule-content-edit",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("content_id").description("스케줄 컨텐츠 id")
                ),
                requestFields(
                        titleFieldWithPath()
                                .attributes(getConstraintAttributes(contentEditCD, "title")),
                        fieldWithPath("content").type(STRING).description("내용")
                                .attributes(getConstraintAttributes(contentEditCD, "content")),
                        subsectionWithPath("timePeriod").type(OBJECT).description("기간")
                                .attributes(getConstraintAttributes(contentEditCD, "timePeriod")),
                        fieldWithPath("cost").type(NUMBER).description("비용")
                                .attributes(getConstraintAttributes(contentEditCD, "cost")),
                        fieldWithPath("validTimePeriod").type(BOOLEAN).description("기간 검증 여부")
                                .attributes(getConstraintAttributes(contentEditCD, "validTimePeriod"))
                ),
                requestFields(
                        beneathPath("timePeriod").withSubsectionId("time-period"),
                        dateTimeFieldWithPath("startDateTime", "시작 시간")
                                .attributes(getConstraintAttributes(timePeriodCD, "startDateTime")),
                        dateTimeFieldWithPath("endDateTime", "종료 시간")
                                .attributes(getConstraintAttributes(timePeriodCD, "endDateTime"))
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("스케줄 컨텐츠 삭제")
    @WithMockCustomUser
    void delete_schedule_content() throws Exception {
        // given
        Member authMember = finder.getLoggedInMember();
        Schedule schedule = createSchedule(authMember);
        ScheduleContent content = createContent(schedule);

        // when
        ResultActions result = this.mockMvc.perform(
                delete(BASIC_URL + "/contents/{content_id}", content.getId())
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "{access_token}")
                        .cookie(addRefreshTokenCookie("{refresh_token}"))
        ).andExpect(status().isOk());

        // then
        result.andDo(document("schedule-content-delete",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("content_id").description("스케줄 컨텐츠 id")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("스케줄 컨텐츠 다건 조회")
    void get_schedule_content_list() throws Exception {
        // given
        Member member = createMember();
        Schedule schedule = createSchedule(member);
        List<ScheduleContent> contents = createContents(schedule);

        // when
        ResultActions result = this.mockMvc.perform(
                        get(BASIC_URL + "/{schedule_id}/detail", schedule.getId())
                                .accept(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(contents.size())))
                .andExpect(jsonPath("$..[?(@.title == '%s')]", contents.get(0).getTitle()).exists())
                .andExpect(jsonPath("$..[?(@.content == '%s')]", contents.get(1).getContent()).exists())
                .andExpect(jsonPath("$..[?(@.cost == '%s')]", contents.get(2).getCost()).exists());

        // then
        result.andDo(document("schedule-content-list-get",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                        parameterWithName("schedule_id").description("스케줄 id")
                ),
                customResponseFields("response",
                        beneathPath("data[]").withSubsectionId("data"),
                        attributes(getTitleAttributes("Schedule Content Response Fields")),
                        idFieldWithPath(),
                        titleFieldWithPath(),
                        contentFieldWithPath(),
                        subsectionWithPath("timePeriod").type(OBJECT).description("기간"),
                        fieldWithPath("cost").type(NUMBER).description("비용")
                ),
                customResponseFields("format-response",
                        beneathPath("data[].timePeriod").withSubsectionId("time-period"),
                        attributes(getTitleAttributes("Time Period Response Fields")),
                        dateTimeFieldWithPath("startDateTime", "시작 시간"),
                        dateTimeFieldWithPath("endDateTime", "종료 시간")
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

    Schedule createSchedule(Member member) {
        ScheduleCreate create =
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
                        .build();

        return scheduleRepository.save(create.toEntity(member));
    }

    ScheduleContent createContent(Schedule schedule) {
        ScheduleContentCreate create = ScheduleContentCreate.builder()
                .title("schedule")
                .content("content")
                .timePeriod(
                        TimePeriodRequest.builder()
                                .startDateTime(getDateTime(true, schedule, 6))
                                .endDateTime(getDateTime(false, schedule, 6))
                                .build()
                )
                .cost(5000)
                .build();
        return scheduleContentRepository.save(create.toEntity(schedule));
    }

    List<ScheduleContent> createContents(Schedule schedule) {
        List<ScheduleContentCreate> creates = List.of(
                ScheduleContentCreate.builder()
                        .title("schedule one")
                        .content("content one")
                        .timePeriod(
                                TimePeriodRequest.builder()
                                        .startDateTime(getDateTime(true, schedule, 6))
                                        .endDateTime(getDateTime(false, schedule, 6))
                                        .build()
                        )
                        .cost(5000)
                        .build(),
                ScheduleContentCreate.builder()
                        .title("my schedule two")
                        .content("content two")
                        .timePeriod(
                                TimePeriodRequest.builder()
                                        .startDateTime(getDateTime(true, schedule, 10))
                                        .endDateTime(getDateTime(false, schedule, 10))
                                        .build()
                        )
                        .cost(10000)
                        .build(),
                ScheduleContentCreate.builder()
                        .title("my schedule three")
                        .content("content three")
                        .timePeriod(
                                TimePeriodRequest.builder()
                                        .startDateTime(getDateTime(true, schedule, 13))
                                        .endDateTime(getDateTime(false, schedule, 13))
                                        .build()
                        )
                        .cost(15000)
                        .build());
        return scheduleContentRepository.saveAll(
                creates.stream().
                        map(scheduleCreate -> scheduleCreate.toEntity(schedule))
                        .collect(Collectors.toList()));
    }

    LocalDateTime getDateTime(boolean isStart, Schedule schedule, int hour) {
        return isStart
                ? schedule.getPeriod().getStartDate().atTime(hour, 0)
                : schedule.getPeriod().getStartDate().atTime(hour + 2, 0);
    }
}
