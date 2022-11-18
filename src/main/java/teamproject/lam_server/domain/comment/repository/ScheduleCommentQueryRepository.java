package teamproject.lam_server.domain.comment.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.comment.dto.response.BestCommentResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentProfileResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentReplyResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.QScheduleComment;
import teamproject.lam_server.domain.member.entity.QMember;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static teamproject.lam_server.domain.comment.entity.QScheduleComment.scheduleComment;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.schedule.entity.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class ScheduleCommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<CommentResponse> getComments(Long scheduleId, Pageable pageable) {
        List<Long> ids = queryFactory.select(scheduleComment.id)
                .from(scheduleComment)
                .join(scheduleComment.schedule, schedule)
                .where(
                        parentIdNull(),
                        scheduleIdEq(scheduleId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(scheduleComment.id.desc())
                .fetch();
        QScheduleComment replyComment = new QScheduleComment("replyComment");
        QMember replyMember = new QMember("replyMember");

        List<CommentResponse> contents = queryFactory.from(scheduleComment)
                .join(scheduleComment.member, member)
                .leftJoin(scheduleComment.children, replyComment)
                .leftJoin(replyComment.member, replyMember)
                .where(commentIdIn(ids))
                .transform(
                        groupBy(scheduleComment.id).list(
                                Projections.constructor(CommentResponse.class,
                                        scheduleComment.id,
                                        scheduleComment.comment,
                                        Projections.constructor(CommentProfileResponse.class,
                                                member.nickname,
                                                member.image
                                        ),
                                        scheduleComment.createdDate,
                                        scheduleComment.numberOfLikes,
                                        scheduleComment.numberOfDislikes,
                                        GroupBy.list(
                                                Projections.constructor(CommentReplyResponse.class,
                                                        replyComment.id,
                                                        replyComment.comment,
                                                        Projections.constructor(CommentProfileResponse.class,
                                                                replyMember.nickname,
                                                                replyMember.image
                                                        ),
                                                        replyComment.createdDate,
                                                        replyComment.numberOfLikes,
                                                        replyComment.numberOfDislikes,
                                                        replyComment.parent.id
                                                )
                                        )
                                )
                        ));

        return new PageImpl<>(contents, pageable, ids.size());
    }

    public List<BestCommentResponse> getBestComments(Long scheduleId) {
        List<Long> ids = queryFactory.select(scheduleComment.id)
                .from(scheduleComment)
                .join(scheduleComment.schedule, schedule)
                .where(
                        parentIdNull(),
                        scheduleIdEq(scheduleId),
                        numberOfLikesNotZero()
                )
                .orderBy(scheduleComment.numberOfLikes.desc())
                .limit(3)
                .fetch();


        return queryFactory.select(Projections.constructor(BestCommentResponse.class,
                        scheduleComment.id,
                        scheduleComment.comment,
                        Projections.constructor(CommentProfileResponse.class,
                                member.nickname,
                                member.image
                        ),
                        scheduleComment.createdDate,
                        scheduleComment.numberOfLikes,
                        scheduleComment.numberOfDislikes))
                .from(scheduleComment)
                .leftJoin(scheduleComment.member, member)
                .where(commentIdIn(ids))
                .fetch();
    }

    private BooleanExpression scheduleIdEq(Long id) {
        return id != null ? schedule.id.eq(id) : null;
    }

    private BooleanExpression parentIdNull() {
        return scheduleComment.parent.id.isNull();
    }

    private BooleanExpression commentIdIn(List<Long> ids) {
        return scheduleComment.id.in(ids);
    }

    private BooleanExpression numberOfLikesNotZero() {
        return scheduleComment.numberOfLikes.ne(0);
    }
}
