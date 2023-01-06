package com.lam.liveamonthapp.domain.comment.repository;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import com.lam.liveamonthapp.domain.comment.dto.response.BestCommentResponse;
import com.lam.liveamonthapp.domain.comment.dto.response.CommentProfileResponse;
import com.lam.liveamonthapp.domain.comment.dto.response.CommentReplyResponse;
import com.lam.liveamonthapp.domain.comment.dto.response.CommentResponse;
import com.lam.liveamonthapp.domain.comment.entity.QScheduleComment;
import com.lam.liveamonthapp.domain.member.entity.QMember;
import com.lam.liveamonthapp.global.repository.BasicRepository;

import java.util.Collections;
import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.lam.liveamonthapp.domain.comment.entity.QScheduleComment.scheduleComment;
import static com.lam.liveamonthapp.domain.member.entity.QMember.member;
import static com.lam.liveamonthapp.domain.schedule.entity.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class ScheduleCommentQueryRepository extends BasicRepository {

    private final JPAQueryFactory queryFactory;

    public Page<CommentResponse> getComments(Long scheduleId, Pageable pageable) {
        // predicates
        Predicate[] predicates = {
                parentIdNull(),
                scheduleIdEq(scheduleId)
        };

        // count query
        JPAQuery<Long> countQuery = queryFactory.select(scheduleComment.count())
                .from(scheduleComment)
                .join(scheduleComment.schedule, schedule)
                .where(predicates);

        // covering index
        List<Long> ids = commentIndexQuery()
                .join(scheduleComment.schedule, schedule)
                .where(predicates)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(scheduleComment.id.desc())
                .fetch();

        // contents query
        QScheduleComment replyComment = new QScheduleComment("replyComment");
        QMember replyMember = new QMember("replyMember");
        List<CommentResponse> contents = ids.isEmpty() ? Collections.emptyList()
                : queryFactory.from(scheduleComment)
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

        return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchOne);
    }

    public List<BestCommentResponse> getBestComments(Long scheduleId) {
        // covering index
        List<Long> ids = commentIndexQuery()
                .join(scheduleComment.schedule, schedule)
                .where(
                        parentIdNull(),
                        scheduleIdEq(scheduleId),
                        numberOfLikesNotZero()
                )
                .orderBy(scheduleComment.numberOfLikes.desc())
                .limit(3)
                .fetch();

        // result query
        JPAQuery<BestCommentResponse> resultQuery =
                queryFactory.select(Projections.constructor(BestCommentResponse.class,
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
                        .where(commentIdIn(ids));

        return fetchIndexingQuery(ids.isEmpty(), resultQuery);
    }

    private JPAQuery<Long> commentIndexQuery() {
        return queryFactory.select(scheduleComment.id)
                .from(scheduleComment);
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
