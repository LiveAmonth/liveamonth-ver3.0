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
import teamproject.lam_server.domain.comment.entity.ReviewComment;

import java.util.List;

import static teamproject.lam_server.domain.comment.entity.QReviewComment.reviewComment;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewCommentRepositoryImpl implements ReviewCommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReviewComment> getReviewComments(Long scheduleId, Pageable pageable) {
        List<ReviewComment> elements = getScheduleElementsQuery(scheduleId)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reviewComment.id.desc())
                .fetch();
        JPAQuery<Long> countQuery = getScheduleCountQuery(scheduleId);

        return PageableExecutionUtils.getPage(
                elements,
                pageable,
                countQuery::fetchOne);
    }

    @Override
    public List<ReviewComment> getReviewCommentReplies(Long scheduleId, Long from, Long to) {
        return queryFactory.selectFrom(reviewComment)
                .leftJoin(reviewComment.review, review).fetchJoin()
                .leftJoin(reviewComment.member, member).fetchJoin()
                .leftJoin(reviewComment.parent).fetchJoin()
                .where(
                        reviewIdEq(scheduleId),
                        parentIdNotNull(),
                        parentIdBetween(from, to)
                )
                .orderBy(reviewComment.id.desc())
                .fetch();
    }

    private JPAQuery<ReviewComment> getScheduleElementsQuery(Long scheduleId) {
        return queryFactory.select(reviewComment)
                .from(reviewComment)
                .leftJoin(reviewComment.review, review).fetchJoin()
                .leftJoin(reviewComment.member, member).fetchJoin()
                .where(
                        reviewIdEq(scheduleId),
                        parentIdNull()
                );
    }

    private JPAQuery<Long> getScheduleCountQuery(Long scheduleId) {
        return queryFactory.select(reviewComment.count())
                .from(reviewComment)
                .where(
                        reviewIdEq(scheduleId),
                        parentIdNull()
                );
    }

    private BooleanExpression reviewIdEq(Long id) {
        return id != null ? review.id.eq(id) : null;
    }

    private BooleanExpression parentIdNull() {
        return reviewComment.parent.id.isNull();
    }

    private BooleanExpression parentIdNotNull() {
        return reviewComment.parent.id.isNotNull();
    }

    private BooleanExpression parentIdBetween(Long min, Long max) {
        return reviewComment.parent.id.between(min, max);
    }
}