package teamproject.lam_server.domain.interaction.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.interaction.entity.ReviewLike;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

import static teamproject.lam_server.domain.interaction.entity.QReviewLike.reviewLike;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewLikeRepositoryImpl implements ReviewLikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<ReviewLike> findById(Long from, Long to) {
        return Optional.ofNullable(
                queryFactory.selectFrom(reviewLike)
                        .join(reviewLike.from, member).fetchJoin()
                        .join(reviewLike.to, review).fetchJoin()
                        .where(
                                fromMemberIdEq(from),
                                toReviewIdEq(to)
                        )
                        .fetchOne()
        );
    }

    @Override
    public Long getReviewLikesCount(Long to) {
        return queryFactory.select(reviewLike.count())
                .join(reviewLike.to, review).fetchJoin()
                .where(
                        toReviewIdEq(to)
                )
                .fetchOne();
    }


    @Override
    public List<Member> getReviewLikes(Long to) {
        return queryFactory.select(reviewLike.from)
                .join(reviewLike.from, member).fetchJoin()
                .leftJoin(reviewLike.to, review)
                .where(
                        toReviewIdEq(to)
                )
                .fetch();
    }

    @Override
    public Long getLikedReviewsCount(Long from) {
        return queryFactory.select(reviewLike.count())
                .join(reviewLike.from, member).fetchJoin()
                .where(
                        fromMemberIdEq(from)
                )
                .fetchOne();
    }

    @Override
    public List<Review> getLikedReviews(Long from) {
        return queryFactory.select(reviewLike.to)
                .leftJoin(reviewLike.from, member)
                .join(reviewLike.to, review).fetchJoin()
                .where(
                        fromMemberIdEq(from)
                )
                .fetch();
    }

    private BooleanExpression fromMemberIdEq(Long from) {
        return from != null ? member.id.eq(from) : null;
    }

    private BooleanExpression toReviewIdEq(Long to) {
        return to != null ? review.id.eq(to) : null;
    }
}
