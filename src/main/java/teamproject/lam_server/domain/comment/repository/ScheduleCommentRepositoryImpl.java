package teamproject.lam_server.domain.comment.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.comment.entity.ScheduleComment;

import java.util.List;

import static teamproject.lam_server.domain.comment.entity.QScheduleComment.scheduleComment;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ScheduleCommentRepositoryImpl implements ScheduleCommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ScheduleComment> getScheduleComments(Long scheduleId, Pageable pageable) {
        List<ScheduleComment> elements = getScheduleElementsQuery(scheduleId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(scheduleComment.id.desc())
                .fetch();
        JPAQuery<Long> countQuery = getScheduleCountQuery(scheduleId);

        return PageableExecutionUtils.getPage(
                elements,
                pageable,
                countQuery::fetchOne);
    }

    @Override
    public List<ScheduleComment> getScheduleCommentReplies(Long scheduleId, Long from, Long to) {
        return queryFactory.selectFrom(scheduleComment)
                .leftJoin(scheduleComment.schedule, schedule).fetchJoin()
                .leftJoin(scheduleComment.member, member).fetchJoin()
                .leftJoin(scheduleComment.parent).fetchJoin()
                .where(
                        scheduleIdEq(scheduleId),
                        parentIdNotNull(),
                        parentIdBetween(from, to)
                )
                .orderBy(scheduleComment.id.desc())
                .fetch();
    }

    private JPAQuery<ScheduleComment> getScheduleElementsQuery(Long scheduleId) {
        return queryFactory.select(scheduleComment)
                .from(scheduleComment)
                .leftJoin(scheduleComment.schedule, schedule).fetchJoin()
                .leftJoin(scheduleComment.member, member).fetchJoin()
                .where(
                        scheduleIdEq(scheduleId),
                        parentIdNull()
                );
    }

    private JPAQuery<Long> getScheduleCountQuery(Long scheduleId) {
        return queryFactory.select(scheduleComment.count())
                .from(scheduleComment)
                .where(
                        scheduleIdEq(scheduleId),
                        parentIdNull()
                );
    }

    private BooleanExpression scheduleIdEq(Long id) {
        return id != null ? schedule.id.eq(id) : null;
    }

    private BooleanExpression parentIdNull() {
        return scheduleComment.parent.id.isNull();
    }

    private BooleanExpression parentIdNotNull() {
        return scheduleComment.parent.id.isNotNull();
    }

    private BooleanExpression parentIdBetween(Long min, Long max) {
        return scheduleComment.parent.id.between(min, max);
    }
}
