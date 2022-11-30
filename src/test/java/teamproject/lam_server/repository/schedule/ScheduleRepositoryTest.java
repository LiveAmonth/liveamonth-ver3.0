package teamproject.lam_server.repository.schedule;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.types.Projections.constructor;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;
import static teamproject.lam_server.utils.ResultUtils.getPerformanceImprovementRate;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("스케줄 조회 성능 비교 인덱스")
    void compare_get_schedule_index() {
        // given
        int count = 10000;
        Member savedMember = saveMember();
        saveScheduleWithBulk(savedMember, count);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<Long> pagingTimes = new ArrayList<>();
        List<Long> indexTimes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            queryFactory.select(schedule.count())
                    .from(schedule)
                    .join(schedule.member, member)
                    .where(member.nickname.eq(savedMember.getNickname()))
                    .fetch();

            queryFactory.select(getScheduleCardProjection())
                    .from(schedule)
                    .join(schedule.member, member)
                    .where(member.nickname.eq(savedMember.getNickname()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(schedule.id.desc())
                    .fetch();

            long stopTime = System.currentTimeMillis();
            long pagingTime = stopTime - startTime;
            pagingTimes.add(pagingTime);
            em.clear();

            startTime = System.currentTimeMillis();
            queryFactory.select(schedule.count())
                    .from(schedule)
                    .join(schedule.member, member)
                    .where(member.nickname.eq(savedMember.getNickname()))
                    .fetch();

            List<Long> ids = queryFactory.select(schedule.id)
                    .from(schedule)
                    .join(schedule.member, member)
                    .where(member.nickname.eq(savedMember.getNickname()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(schedule.id.desc())
                    .fetch();

            queryFactory.select(getScheduleCardProjection())
                    .from(schedule)
                    .join(schedule.member, member)
                    .where(schedule.id.in(ids))
                    .fetch();
            stopTime = System.currentTimeMillis();
            long indexTime = stopTime - startTime;
            indexTimes.add(indexTime);
            em.clear();
        }

        // then
        double avgPagingTime = pagingTimes.stream().mapToDouble(Long::longValue).average().getAsDouble();
        double avgIndexTime = indexTimes.stream().mapToDouble(Long::longValue).average().getAsDouble();
        log.info("== 결과(" + count + "건 기준) ==");
        log.info("기존 페이징 조회 평균 속도={}ms", String.format("%.0f", avgPagingTime));
        log.info("커버링 인덱스 조회 평균 속도={}ms", String.format("%.0f", avgIndexTime));
        log.info("높은 성능={}, 성능 개선율={}",
                avgPagingTime < avgIndexTime ? "기존 페이징" : "커버링 인덱스",
                getPerformanceImprovementRate(avgPagingTime, avgIndexTime));
    }

    @Test
    @DisplayName("스케줄 조회 성능 비교 Projection")
    void compare_get_schedule_projection() {
        // given
        int count = 10000;
        Member savedMember = saveMember();
        saveScheduleWithBulk(savedMember, count);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<Long> entityTimes = new ArrayList<>();
        List<Long> projectionTimes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            List<Long> ids = queryFactory.select(schedule.id)
                    .from(schedule)
                    .join(schedule.member, member)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(schedule.id.desc())
                    .fetch();

            List<Schedule> list = queryFactory.selectFrom(schedule)
                    .where(schedule.id.in(ids))
                    .join(schedule.member, member).fetchJoin()
                    .fetch();

            List<ScheduleCardDto> result = list.stream().map(ScheduleCardDto::of).collect(Collectors.toList());

            long stopTime = System.currentTimeMillis();
            long entityTime = stopTime - startTime;
            entityTimes.add(entityTime);
            em.clear();

            startTime = System.currentTimeMillis();
            ids = queryFactory.select(schedule.id)
                    .from(schedule)
                    .join(schedule.member, member)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .orderBy(schedule.id.desc())
                    .fetch();

            queryFactory.select(getScheduleCardProjection())
                    .from(schedule)
                    .join(schedule.member, member)
                    .where(schedule.id.in(ids))
                    .fetch();
            stopTime = System.currentTimeMillis();
            long projectionTime = stopTime - startTime;
            projectionTimes.add(projectionTime);
            em.clear();
        }


        // then
        double avgEntityTime = entityTimes.stream().mapToDouble(Long::longValue).average().getAsDouble();
        double avgProjectionTime = projectionTimes.stream().mapToDouble(Long::longValue).average().getAsDouble();
        log.info("== 결과(" + count + "건 기준) ==");
        log.info("Entity 조회 평균 속도={}ms", String.format("%.0f", avgEntityTime));
        log.info("DTO 조회 평균 속도={}ms", String.format("%.0f", avgProjectionTime));
        log.info("높은 성능={}, 성능 개선율={}",
                avgEntityTime < avgProjectionTime ? "Entity 조회" : "DTO 조회",
                getPerformanceImprovementRate(avgEntityTime, avgProjectionTime));
    }

    @Test
    @DisplayName("커버링 인덱스 성능 비교")
    void compare_covering_index() {
        // given
        int count = 10000;
        Member savedMember = saveMember();
        saveScheduleWithBulk(savedMember, count);

        // when
        List<Long> basicTimes = new ArrayList<>();
        List<Long> indexTimes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
            queryFactory.select(getScheduleCardProjection())
                    .from(schedule)
                    .join(schedule.member, member)
                    .limit(5)
                    .orderBy(schedule.numberOfLikes.desc())
                    .fetch();

            long stopTime = System.currentTimeMillis();
            long basicQueryTime = stopTime - startTime;
            basicTimes.add(basicQueryTime);
            em.clear();

            startTime = System.currentTimeMillis();
            List<Long> ids = queryFactory.select(schedule.id)
                    .from(schedule)
                    .limit(5)
                    .orderBy(schedule.numberOfLikes.desc())
                    .fetch();

            // result query
            queryFactory.select(getScheduleCardProjection())
                    .from(schedule)
                    .join(schedule.member, member)
                    .where(schedule.id.in(ids))
                    .orderBy(schedule.numberOfLikes.desc())
                    .fetch();

            stopTime = System.currentTimeMillis();
            long indexTime = stopTime - startTime;
            indexTimes.add(indexTime);
            em.clear();
        }


        // then
        double avgBasicQueryTime = basicTimes.stream().mapToDouble(Long::longValue).average().getAsDouble();
        double avgIndexTime = indexTimes.stream().mapToDouble(Long::longValue).average().getAsDouble();
        log.info("== 결과(" + 10000 + "건 기준) ==");
        log.info("기존 리스트 조회 평균 속도={}ms", String.format("%.0f", avgBasicQueryTime));
        log.info("커버링 인덱스 조회 평균 속도={}ms", String.format("%.0f", avgIndexTime));
        log.info("높은 성능={}, 성능 개선율={}",
                avgBasicQueryTime < avgIndexTime ? "기존 리스트 조회" : "커버링 인덱스",
                getPerformanceImprovementRate(avgBasicQueryTime, avgIndexTime));
    }

    private void saveScheduleWithBulk(Member savedMember, int count) {
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
    }

    private Member saveMember() {
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

    ConstructorExpression<ScheduleCardResponse> getScheduleCardProjection() {
        return constructor(ScheduleCardResponse.class,
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
        );
    }

    @Getter
    @Builder
    static class ScheduleCardDto {

        private Long id;
        private String title;
        private CityName city;
        private SimpleProfileDto profile;
        private long cost;
        private long numberOfHits;
        private long numberOfLikes;
        private long numberOfComments;
        private PeriodResponse period;
        private boolean publicFlag;

        static ScheduleCardDto of(Schedule schedule) {
            return ScheduleCardDto.builder()
                    .id(schedule.getId())
                    .title(schedule.getTitle())
                    .city(schedule.getCityName())
                    .profile(SimpleProfileDto.of(schedule.getMember()))
                    .cost(schedule.getTotalCost())
                    .numberOfHits(schedule.getNumberOfHits())
                    .numberOfLikes(schedule.getNumberOfLikes())
                    .numberOfComments(schedule.getNumberOfComments())
                    .period(new PeriodResponse(schedule.getPeriod().getStartDate(), schedule.getPeriod().getEndDate()))
                    .publicFlag(schedule.getPublicFlag())
                    .build();
        }
    }

    @Getter
    @Builder
    static class SimpleProfileDto {
        private Long id;
        private String loginId;
        private String nickname;
        private String image;
        private long numberOfReviews;
        private long numberOfSchedules;
        private long numberOfFollowers;
        private long numberOfFollows;

        static SimpleProfileDto of(Member member) {
            return SimpleProfileDto.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .image(member.getImage())
                    .loginId(member.getLoginId())
                    .numberOfReviews(member.getNumberOfReviews())
                    .numberOfSchedules(member.getNumberOfSchedules())
                    .numberOfFollowers(member.getNumberOfFollowers())
                    .numberOfFollows(member.getNumberOfFollows())
                    .build();
        }
    }

}
