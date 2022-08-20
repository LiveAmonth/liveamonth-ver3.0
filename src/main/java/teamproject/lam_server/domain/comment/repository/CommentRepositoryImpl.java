package teamproject.lam_server.domain.comment.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
public class CommentRepositoryImpl implements CommentRepositoryCustom {
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

    private JPAQuery<ScheduleComment> getScheduleElementsQuery(Long scheduleId) {
        return queryFactory.selectFrom(scheduleComment)
                .leftJoin(scheduleComment.parent).fetchJoin()
                .leftJoin(scheduleComment.member, member).fetchJoin()
                .where(
                        scheduleIdEq(scheduleId)
                );
    }

    private JPAQuery<Long> getScheduleCountQuery(Long scheduleId) {
        return queryFactory.select(scheduleComment.count())
                .from(scheduleComment)
                .where(scheduleIdEq(scheduleId));
    }

    private BooleanExpression scheduleIdEq(Long id) {
        return id != null ? schedule.id.eq(id) : null;
    }
}
