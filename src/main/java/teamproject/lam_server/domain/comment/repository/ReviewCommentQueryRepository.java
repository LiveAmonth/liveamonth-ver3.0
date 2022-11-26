package teamproject.lam_server.domain.comment.repository;

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
import teamproject.lam_server.domain.comment.dto.response.BestCommentResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentProfileResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentReplyResponse;
import teamproject.lam_server.domain.comment.dto.response.CommentResponse;
import teamproject.lam_server.domain.comment.entity.QReviewComment;
import teamproject.lam_server.domain.member.entity.QMember;
import teamproject.lam_server.global.repository.BasicRepository;

import java.util.Collections;
import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static teamproject.lam_server.domain.comment.entity.QReviewComment.reviewComment;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewCommentQueryRepository extends BasicRepository {

    private final JPAQueryFactory queryFactory;

    public Page<CommentResponse> getComments(Long reviewId, Pageable pageable) {
        // predicates
        Predicate[] predicates = {
                parentIdNull(),
                reviewIdEq(reviewId)
        };

        // count query
        JPAQuery<Long> countQuery = queryFactory.select(reviewComment.count())
                .from(reviewComment)
                .join(reviewComment.review, review)
                .where(predicates);

        // covering index
        List<Long> ids = commentIndexQuery()
                .join(reviewComment.review, review)
                .where(predicates)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reviewComment.id.desc())
                .fetch();

        // contents query
        QReviewComment replyComment = new QReviewComment("replyComment");
        QMember replyMember = new QMember("replyMember");
        List<CommentResponse> contents = ids.isEmpty() ? Collections.emptyList()
                : queryFactory.from(reviewComment)
                .join(reviewComment.member, member)
                .leftJoin(reviewComment.children, replyComment)
                .leftJoin(replyComment.member, replyMember)
                .where(commentIdIn(ids))
                .transform(
                        groupBy(reviewComment.id).list(
                                Projections.constructor(CommentResponse.class,
                                        reviewComment.id,
                                        reviewComment.comment,
                                        Projections.constructor(CommentProfileResponse.class,
                                                member.nickname,
                                                member.image
                                        ),
                                        reviewComment.createdDate,
                                        reviewComment.numberOfLikes,
                                        reviewComment.numberOfDislikes,
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

    public List<BestCommentResponse> getBestComments(Long reviewId) {
        // covering index
        List<Long> ids = commentIndexQuery()
                .join(reviewComment.review, review)
                .where(
                        parentIdNull(),
                        reviewIdEq(reviewId),
                        numberOfLikesNotZero()
                )
                .orderBy(reviewComment.numberOfLikes.desc())
                .limit(3)
                .fetch();


        // result query
        JPAQuery<BestCommentResponse> resultQuery =
                queryFactory.select(Projections.constructor(BestCommentResponse.class,
                                reviewComment.id,
                                reviewComment.comment,
                                Projections.constructor(CommentProfileResponse.class,
                                        member.nickname,
                                        member.image
                                ),
                                reviewComment.createdDate,
                                reviewComment.numberOfLikes,
                                reviewComment.numberOfDislikes))
                        .from(reviewComment)
                        .join(reviewComment.member, member)
                        .where(commentIdIn(ids));

        return fetchIndexingQuery(ids.isEmpty(), resultQuery);
    }

    private JPAQuery<Long> commentIndexQuery() {
        return queryFactory.select(reviewComment.id)
                .from(reviewComment);
    }

    private BooleanExpression reviewIdEq(Long id) {
        return id != null ? review.id.eq(id) : null;
    }

    private BooleanExpression parentIdNull() {
        return reviewComment.parent.id.isNull();
    }

    private BooleanExpression commentIdIn(List<Long> ids) {
        return reviewComment.id.in(ids);
    }

    private BooleanExpression numberOfLikesNotZero() {
        return reviewComment.numberOfLikes.ne(0);
    }
}
