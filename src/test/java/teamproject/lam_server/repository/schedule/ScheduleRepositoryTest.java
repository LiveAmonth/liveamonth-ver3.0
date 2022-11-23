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
import static com.querydsl.core.types.dsl.Expressions.asString;
import static teamproject.lam_server.constants.AttrConstants.IMAGEBB_URL;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;
import static teamproject.lam_server.utils.ResultUtils.getPerformanceImprovementRate;

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
    }

    @Test
    @DisplayName("스케줄 조회 성능 비교 인덱스")
    void compare_get_schedule_index() {
        // given
        int count = 1000;
        Pageable pageable = PageRequest.of(0, 10);

        List<ScheduleCreate> scheduleCreates = new ArrayList<>();
        for (int j = 0; j < count; j++) {
            scheduleCreates.add(ScheduleCreate.builder()
                    .title("title" + j)
                    .publicFlag(true)
                    .city(CityName.SE.name())
                    .period(PeriodRequest.builder()
                            .startDate(LocalDate.now())
                            .endDate(LocalDate.now().plusDays(20))
                            .build())
                    .build());
        }
        scheduleJdbcRepository.batchScheduleInsert(scheduleCreates, savedMember.getId());

        // when
        long startTime = System.currentTimeMillis();
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
        long stopTime = System.currentTimeMillis();
        long pagingTime = stopTime - startTime;

        startTime = System.currentTimeMillis();
        List<Long> ids = queryFactory.select(schedule.id)
                .from(schedule)
                .join(schedule.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(schedule.id.desc())
                .fetch();

        queryFactory.selectFrom(schedule)
                .where(schedule.id.in(ids))
                .fetch();
        stopTime = System.currentTimeMillis();
        long indexTime = stopTime - startTime;

        // thenF
        log.info("============== 결과(" + count + "건 기준) ================");
        log.info("기존 페이징={}s", pagingTime);
        log.info("커버링 인덱스={}s", indexTime);
        log.info("더 빠른 방식={}, 성능 개선율={}", pagingTime < indexTime ? "Paging" : "Index", getPerformanceImprovementRate(pagingTime, indexTime));
    }

    @Test
    @DisplayName("스케줄 조회 성능 비교 Projection")
    void compare_get_schedule_projection() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<ScheduleCreate> scheduleCreates = new ArrayList<>();
        int count = 1000000;
        for (int j = 0; j < count; j++) {
            scheduleCreates.add(ScheduleCreate.builder()
                    .title("title" + j)
                    .publicFlag(true)
                    .city(CityName.SE.name())
                    .period(PeriodRequest.builder()
                            .startDate(LocalDate.now())
                            .endDate(LocalDate.now().plusDays(20))
                            .build())
                    .build());
        }
        scheduleJdbcRepository.batchScheduleInsert(scheduleCreates, savedMember.getId());
        // when
        long startTime = System.currentTimeMillis();
        List<Long> ids = queryFactory.select(schedule.id)
                .from(schedule)
                .join(schedule.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(schedule.id.desc())
                .fetch();

        queryFactory.selectFrom(schedule)
                .where(schedule.id.in(ids))
                .join(schedule.member, member).fetchJoin()
                .fetch();

        long stopTime = System.currentTimeMillis();
        long entityTime = stopTime - startTime;

        startTime = System.currentTimeMillis();
        ids = queryFactory.select(schedule.id)
                .from(schedule)
                .join(schedule.member, member)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(schedule.id.desc())
                .fetch();

        queryFactory.select(
                        constructor(ScheduleCardResponse.class,
                                schedule.id,
                                schedule.title,
                                schedule.cityName,
                                constructor(SimpleProfileResponse.class,
                                        member.id,
                                        member.loginId,
                                        member.nickname,
                                        asString(IMAGEBB_URL + member.image),
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
        long projectionTime = stopTime - startTime;

        // then
        log.info("============== 결과(" + count + "건 기준) ================");
        log.info("엔티티 조회={}ms", entityTime);
        log.info("DTO 조회={}ms", projectionTime);
        log.info("더 빠른 방식={}, 성능 개선율={}", entityTime < projectionTime ? "Entity" : "DTO", getPerformanceImprovementRate(entityTime, projectionTime));
    }
}
