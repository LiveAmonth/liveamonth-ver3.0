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
import teamproject.lam_server.domain.comment.entity.QReviewComment;
import teamproject.lam_server.domain.member.entity.QMember;

import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static teamproject.lam_server.domain.comment.entity.QReviewComment.reviewComment;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewCommentQueryRepository {

    private final JPAQueryFactory queryFactory;

    public Page<CommentResponse> getComments(Long reviewId, Pageable pageable) {
        List<Long> ids = queryFactory.select(reviewComment.id)
                .from(reviewComment)
                .join(reviewComment.review, review)
                .where(
                        parentIdNull(),
                        reviewIdEq(reviewId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(reviewComment.id.desc())
                .fetch();

        QReviewComment replyComment = new QReviewComment("replyComment");
        QMember replyMember = new QMember("replyMember");

        List<CommentResponse> contents = queryFactory.from(reviewComment)
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

        return new PageImpl<>(contents, pageable, ids.size());
    }

    public List<BestCommentResponse> getBestComments(Long reviewId) {
        List<Long> ids = queryFactory.select(reviewComment.id)
                .from(reviewComment)
                .join(reviewComment.review, review)
                .where(
                        parentIdNull(),
                        reviewIdEq(reviewId),
                        numberOfLikesNotZero()
                )
                .orderBy(reviewComment.numberOfLikes.desc())
                .limit(3)
                .fetch();


        return queryFactory.select(Projections.constructor(BestCommentResponse.class,
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
                .where(commentIdIn(ids))
                .fetch();
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
