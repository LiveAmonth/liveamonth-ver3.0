package teamproject.lam_server.repository.schedule;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.request.ScheduleCreate;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.domain.schedule.repository.core.ScheduleRepository;
import teamproject.lam_server.global.dto.request.PeriodRequest;
import teamproject.lam_server.global.dto.response.PeriodResponse;
import teamproject.lam_server.jdbc.schedule.ScheduleJdbcRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class ScheduleRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JPAQueryFactory queryFactory;
    @Autowired
    ScheduleJdbcRepository scheduleJdbcRepository;
    @Autowired
    ScheduleRepository scheduleRepository;
    Member savedMember;

    @BeforeAll
    void setUp() {
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
        savedMember = memberRepository.save(memberCreate.toEntity(passwordEncoder));

        for (int i = 0; i < 10; i++) {
            List<ScheduleCreate> scheduleCreates = new ArrayList<>();
            for (int j = 0; j < 100000; j++) {
                scheduleCreates.add(ScheduleCreate.builder()
                        .title("title" + (i * 100000) + j)
                        .publicFlag(true)
                        .city(CityName.SE.name())
                        .period(PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(20))
                                .build())
                        .build());
            }
            scheduleJdbcRepository.batchScheduleInsert(scheduleCreates, savedMember.getId());
        }

        scheduleRepository.save(
                ScheduleCreate.builder()
                        .title("title as")
                        .publicFlag(true)
                        .city(CityName.SE.name())
                        .period(PeriodRequest.builder()
                                .startDate(LocalDate.now())
                                .endDate(LocalDate.now().plusDays(20))
                                .build())
                        .build().toEntity(savedMember)
        );

    }

    @Test
    @DisplayName("스케줄 조회 성능 비교 인덱스")
    void compare_get_schedule_index() {
        // given
        long startTime;
        long stopTime;
        long pagingTime;
        long indexTime;
        Pageable pageable = PageRequest.of(0, 10);

        // when
        startTime = System.currentTimeMillis();
        List<Schedule> elements = queryFactory.selectFrom(schedule)
                .join(schedule.member, member).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(schedule.id.desc())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(schedule.count())
                .from(schedule)
                .join(schedule.member, member);

        Page<Schedule> resultByPaging = PageableExecutionUtils.getPage(
                elements,
                pageable,
                countQuery::fetchOne);
        stopTime = System.currentTimeMillis();
        pagingTime = stopTime - startTime;

        startTime = System.currentTimeMillis();
        List<Long> ids = queryFactory.select(schedule.id)
                .from(schedule)
                .join(schedule.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(schedule.id.desc())
                .fetch();

        List<Schedule> contents = queryFactory.selectFrom(schedule)
                .where(schedule.id.in(ids))
                .fetch();
        Page<Schedule> resultByIndex = new PageImpl<>(contents, pageable, ids.size());
        stopTime = System.currentTimeMillis();
        indexTime = stopTime - startTime;


        // then
        log.info("============== 결과 ================");
        log.info("더 빠른 방식={}, 시간차={}", pagingTime < indexTime ? "Paging" : "Index", Math.abs(pagingTime - indexTime));
    }

    @Test
    @DisplayName("스케줄 조회 성능 비교 Projection")
    void compare_get_schedule_projection() {
        // given
        long startTime;
        long stopTime;
        long entityTime;
        long projectionTime;
        ScheduleSearchCond cond = ScheduleSearchCond.builder().build();
        Pageable pageable = PageRequest.of(0, 10);

        // when
        startTime = System.currentTimeMillis();
        List<Long> ids = queryFactory.select(schedule.id)
                .from(schedule)
                .join(schedule.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(schedule.id.desc())
                .fetch();

        List<Schedule> result = queryFactory.selectFrom(schedule)
                .where(schedule.id.in(ids))
                .join(schedule.member, member).fetchJoin()
                .fetch();

        stopTime = System.currentTimeMillis();
        entityTime = stopTime - startTime;

        startTime = System.currentTimeMillis();
        ids = queryFactory.select(schedule.id)
                .from(schedule)
                .join(schedule.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(schedule.id.desc())
                .fetch();

        List<ScheduleCardResponse> contents = queryFactory.select(
                        constructor(ScheduleCardResponse.class,
                                schedule.id,
                                schedule.title,
                                schedule.cityName,
                                constructor(SimpleProfileResponse.class,
                                        member.id,
                                        member.loginId,
                                        member.nickname,
                                        member.image,
                                        member.numberOfReviews,
                                        member.numberOfSchedules,
                                        member.numberOfFollowers,
                                        member.numberOfFollows
                                ),
                                schedule.totalCost,
                                schedule.numberOfHits,
                                schedule.numberOfLikes,
                                schedule.numberOfComments,
                                constructor(PeriodResponse.class,
                                        schedule.period.startDate,
                                        schedule.period.endDate
                                ),
                                schedule.publicFlag
                        )
                )
                .from(schedule)
                .join(schedule.member, member)
                .where(schedule.id.in(ids))
                .fetch();
        stopTime = System.currentTimeMillis();
        projectionTime = stopTime - startTime;

        // then
        log.info("============== 결과 ================");
        log.info("더 빠른 방식={}, 시간차={}", entityTime < projectionTime ? "Entity" : "Projection", Math.abs(entityTime - projectionTime));
    }
}
