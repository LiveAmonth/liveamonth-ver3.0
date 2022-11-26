package teamproject.lam_server.domain.schedule.repository.query;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.member.entity.QMember;
import teamproject.lam_server.domain.schedule.dto.condition.ScheduleSearchCond;
import teamproject.lam_server.domain.schedule.dto.response.EditableScheduleResponse;
import teamproject.lam_server.domain.schedule.dto.response.MyScheduleResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleCardResponse;
import teamproject.lam_server.domain.schedule.dto.response.ScheduleContentResponse;
import teamproject.lam_server.domain.schedule.entity.Schedule;
import teamproject.lam_server.global.dto.response.PeriodResponse;
import teamproject.lam_server.global.dto.response.TimePeriodResponse;
import teamproject.lam_server.global.repository.BasicRepository;

import java.time.LocalDate;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.jpa.JPAExpressions.select;
import static org.springframework.util.StringUtils.hasText;
import static teamproject.lam_server.domain.interaction.entity.member.QFollower.follower;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;
import static teamproject.lam_server.domain.schedule.entity.QScheduleContent.scheduleContent;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepository extends BasicRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ScheduleCardResponse> search(ScheduleSearchCond cond, Pageable pageable) {
        // predicates
        Predicate[] predicates = {
                memberNicknameEq(cond.getMemberNickname()),
                cityNameEq(cond.getCityName()),
                startDateGoe(cond.getStartDate()),
                titleContain(cond.getTitle()),
                publicFlag()
        };

        // count query
        JPAQuery<Long> countQuery = queryFactory.select(schedule.count())
                .from(schedule)
                .join(schedule.member, member)
                .where(predicates);

        // covering index
        List<Long> ids = scheduleIndexQuery()
                .join(schedule.member, member)
                .where(predicates)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), Schedule.class, schedule))
                .fetch();

        // contents query
        JPAQuery<ScheduleCardResponse> resultQuery = queryFactory.select(getScheduleCardProjection())
                .from(schedule)
                .join(schedule.member, member)
                .where(scheduleIdIn(ids))
                .orderBy(mapToOrderSpec(pageable.getSort(), Schedule.class, schedule));

        return PageableExecutionUtils.getPage(
                fetchIndexingQuery(ids.isEmpty(), resultQuery),
                pageable,
                countQuery::fetchOne);
    }

    public List<ScheduleContentResponse> getScheduleContents(Long scheduleId) {
        // covering index
        List<Long> ids = contentIndexQuery()
                .where(scheduleIdEqAtContent(scheduleId))
                .fetch();

        // result query
        JPAQuery<ScheduleContentResponse> resultQuery = queryFactory
                .select(constructor(ScheduleContentResponse.class,
                        scheduleContent.id,
                        scheduleContent.title,
                        scheduleContent.content,
                        scheduleContent.cost,
                        constructor(TimePeriodResponse.class,
                                scheduleContent.timePeriod.startDateTime,
                                scheduleContent.timePeriod.endDateTime
                        )
                ))
                .from(scheduleContent)
                .where(scheduleContentIdIn(ids));

        return fetchIndexingQuery(ids.isEmpty(), resultQuery);
    }

    public List<MyScheduleResponse> getMySchedules(String loginId, Integer size, Long lastId) {
        // covering index
        JPAQuery<Long> idQuery = scheduleIndexQuery()
                .where(
                        createdIdEq(loginId),
                        scheduleIdLt(lastId)
                )
                .orderBy(schedule.id.desc());

        List<Long> ids = size != null
                ? idQuery.limit(size).fetch()
                : idQuery.fetch();

        // result query
        JPAQuery<MyScheduleResponse> resultQuery = queryFactory
                .select(constructor(MyScheduleResponse.class,
                        schedule.id,
                        schedule.title,
                        schedule.cityName,
                        schedule.totalCost,
                        constructor(PeriodResponse.class,
                                schedule.period.startDate,
                                schedule.period.endDate
                        ),
                        schedule.publicFlag,
                        schedule.numberOfHits,
                        schedule.numberOfLikes,
                        schedule.numberOfComments
                ))
                .from(schedule)
                .where(scheduleIdIn(ids))
                .orderBy(schedule.id.desc());

        return fetchIndexingQuery(ids.isEmpty(), resultQuery);
    }

    public List<ScheduleCardResponse> getFollowedSchedules(String loginId, Integer size, Long lastId) {
        // covering index
        JPAQuery<Long> idQuery = scheduleIndexQuery()
                .join(schedule.member, member)
                .where(
                        member.in(
                                select(follower.to)
                                        .from(follower)
                                        .leftJoin(follower.from, member)
                                        .where(followedMemberLoginIdEq(loginId))
                        ),
                        scheduleIdLt(lastId),
                        publicFlag()
                )
                .orderBy(schedule.id.desc());

        List<Long> ids = size != null
                ? idQuery.limit(size).fetch()
                : idQuery.fetch();

        // result query
        JPAQuery<ScheduleCardResponse> resultQuery = queryFactory
                .select(getScheduleCardProjection())
                .from(schedule)
                .join(schedule.member, member)
                .where(scheduleIdIn(ids))
                .orderBy(schedule.id.desc());

        return fetchIndexingQuery(ids.isEmpty(), resultQuery);
    }

    public List<EditableScheduleResponse> getEditableSchedules(String loginId) {
        // covering index
        List<Long> ids = scheduleIndexQuery()
                .where(createdIdEq(loginId))
                .fetch();

        // result query
        JPAQuery<EditableScheduleResponse> resultQuery = queryFactory.select(
                        constructor(EditableScheduleResponse.class,
                                schedule.id,
                                schedule.title,
                                schedule.cityName,
                                constructor(PeriodResponse.class,
                                        schedule.period.startDate,
                                        schedule.period.endDate
                                ),
                                schedule.publicFlag
                        )
                ).from(schedule)
                .where(scheduleIdIn(ids));

        return fetchIndexingQuery(ids.isEmpty(), resultQuery);
    }

    public Long getNumberOfFollowedPosts(String loginId) {
        QMember toMember = new QMember("toMember");
        QMember fromMember = new QMember("fromMember");

        return queryFactory.select(toMember.numberOfSchedules.sum())
                .from(follower)
                .leftJoin(follower.from, fromMember)
                .leftJoin(follower.to, toMember)
                .where(fromMember.loginId.eq(loginId))
                .fetchOne();
    }

    private ConstructorExpression<ScheduleCardResponse> getScheduleCardProjection() {
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

    private JPAQuery<Long> scheduleIndexQuery() {
        return queryFactory.select(schedule.id)
                .from(schedule);
    }

    private JPAQuery<Long> contentIndexQuery() {
        return queryFactory.select(scheduleContent.id)
                .from(scheduleContent);
    }

    private BooleanExpression scheduleIdIn(List<Long> ids) {
        return ids != null ? schedule.id.in(ids) : null;
    }

    private BooleanExpression scheduleContentIdIn(List<Long> ids) {
        return ids != null ? scheduleContent.id.in(ids) : null;
    }

    private BooleanExpression scheduleIdEqAtContent(Long id) {
        return id != null ? scheduleContent.schedule.id.eq(id) : null;
    }

    private BooleanExpression memberNicknameEq(String nickname) {
        return hasText(nickname) ? member.nickname.eq(nickname) : null;
    }

    private BooleanExpression createdIdEq(String loginId) {
        return hasText(loginId) ? schedule.createdBy.eq(loginId) : null;
    }

    private BooleanExpression followedMemberLoginIdEq(String loginId) {
        return hasText(loginId) ? follower.from.loginId.eq(loginId) : null;
    }

    private BooleanExpression titleContain(String title) {
        return hasText(title) ? schedule.title.contains(title) : null;
    }

    private BooleanExpression cityNameEq(CityName cityName) {
        return cityName != null ? schedule.cityName.eq(cityName) : null;
    }

    private BooleanExpression startDateGoe(LocalDate start) {
        return start != null ? schedule.period.startDate.goe(start) : null;
    }

    private BooleanExpression publicFlag() {
        return schedule.publicFlag.eq(true);
    }

    private BooleanExpression scheduleIdLt(Long lastId) {
        return lastId != null ? schedule.id.lt(lastId) : null;
    }
}
