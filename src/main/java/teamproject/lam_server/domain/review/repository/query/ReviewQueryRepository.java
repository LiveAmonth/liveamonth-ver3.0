package teamproject.lam_server.domain.review.repository.query;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.constants.ReviewSearchType;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.dto.response.ReviewDetailResponse;
import teamproject.lam_server.domain.review.dto.response.ReviewListResponse;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.domain.review.entity.ReviewTag;
import teamproject.lam_server.global.repository.BasicRepository;

import java.util.*;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.dsl.Expressions.asString;
import static org.springframework.util.StringUtils.hasText;
import static teamproject.lam_server.constants.AttrConstants.IMAGEBB_URL;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;
import static teamproject.lam_server.domain.review.entity.QReviewTag.reviewTag;
import static teamproject.lam_server.domain.review.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
public class ReviewQueryRepository extends BasicRepository {
    private final JPAQueryFactory queryFactory;

    public Page<ReviewListResponse> search(ReviewSearchCond cond, Pageable pageable) {
        List<Long> ids = queryFactory.select(review.id)
                .from(review)
                .where(
                        reviewSearchWordContains(cond.getSearchWord()),
                        categoryIn(cond.getType()),
                        tagContains(findReviewTags(cond.getTags())),
                        categoryEq(cond.getCategory())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), Review.class, review))
                .fetch();

        List<ReviewListResponse> contents =
                queryFactory.select(getReviewListProjection())
                        .from(review)
                        .where(reviewIdIn(ids))
                        .join(review.member, member)
                        .fetch();

        return new PageImpl<>(contents, pageable, ids.size());
    }

    private ConstructorExpression<ReviewListResponse> getReviewListProjection() {
        return Projections.constructor(ReviewListResponse.class,
                review.id,
                member.nickname,
                review.title,
                review.content,
                review.createdDate,
                review.numberOfHits,
                review.numberOfComments,
                review.numberOfLikes
        );
    }

    public List<ReviewListResponse> getReviewByMember(String loginId, Integer size, Long lastId) {
        List<Long> ids = queryFactory.select(review.id)
                .from(review)
                .where(
                        createdIdEq(loginId),
                        reviewIdLt(lastId)
                )
                .limit(size)
                .orderBy(review.id.desc())
                .fetch();

        return queryFactory.select(getReviewListProjection())
                .from(review)
                .join(review.member, member)
                .where(reviewIdIn(ids))
                .fetch();
    }

    public ReviewDetailResponse getReview(Long id) {
        Map<Long, ReviewDetailResponse> transform = queryFactory.from(review)
                .join(review.member, member)
                .leftJoin(review.tags, reviewTag)
                .join(reviewTag.tag, tag)
                .where(reviewIdEq(id))
                .transform(
                        groupBy(review.id).as(
                                constructor(ReviewDetailResponse.class,
                                        Expressions.asNumber(id),
                                        review.title,
                                        constructor(SimpleProfileResponse.class,
                                                member.id,
                                                member.loginId,
                                                member.nickname,
                                                asString(IMAGEBB_URL + member.image),                                                member.numberOfReviews,
                                                member.numberOfSchedules,
                                                member.numberOfFollowers,
                                                member.numberOfFollows
                                        ),
                                        review.content,
                                        review.category,
                                        GroupBy.list(tag.name),
                                        review.createdDate,
                                        review.numberOfHits,
                                        review.numberOfComments,
                                        review.numberOfLikes
                                )
                        )
                );

        return transform.get(id);
    }

    public List<String> getRecommendationTags() {
        return queryFactory.select(tag.name)
                .from(reviewTag)
                .join(reviewTag.tag, tag)
                .groupBy(tag.id)
                .orderBy(tag.id.count().desc())
                .limit(10)
                .fetch();
    }

    public List<Long> findReviewTags(Set<String> tags) {
        return tags == null
                ? Collections.emptyList()
                : queryFactory.select(reviewTag.id)
                .from(review)
                .join(reviewTag.tag, tag)
                .where(tagNameIn(tags))
                .fetch();
    }

    public List<ReviewTag> findReviewTagsByIdAndTag(Long reviewId, Set<String> tags) {
        return queryFactory.selectFrom(reviewTag)
                .join(reviewTag.tag, tag).fetchJoin()
                .where(reviewTag.review.id.eq(reviewId),
                        tagNameIn(tags))
                .fetch();

    }

    private BooleanExpression createdIdEq(String loginId) {
        return hasText(loginId) ? review.createdBy.eq(loginId) : null;
    }

    private BooleanExpression reviewIdEq(Long id) {
        return id != null ? review.id.eq(id) : null;
    }

    private BooleanExpression reviewIdIn(List<Long> ids) {
        return ids != null ? review.id.in(ids) : null;
    }

    private BooleanExpression reviewIdLt(Long lastId) {
        return lastId != null ? review.id.lt(lastId) : null;
    }

    private BooleanExpression tittleContains(String word) {
        return hasText(word) ? review.title.contains(word) : null;
    }

    private BooleanExpression contentContains(String word) {
        return hasText(word) ? review.content.contains(word) : null;
    }

    private BooleanExpression nicknameEq(String word) {
        return hasText(word) ? member.nickname.eq(word) : null;
    }

    private BooleanExpression reviewSearchWordContains(String word) {
        return hasText(word) ? Objects.requireNonNull(tittleContains(word)).or(contentContains(word)).or(nicknameEq(word)) : null;
    }

    private BooleanExpression categoryIn(ReviewSearchType type) {
        return type != null ? review.category.in(type.getSubs()) : null;
    }

    private BooleanExpression categoryEq(ReviewCategory category) {
        return category != null ? review.category.eq(category) : null;
    }

    private BooleanExpression tagContains(List<Long> ids) {
        return !ids.isEmpty() ? review.id.in(ids) : null;
    }

    private BooleanExpression tagNameIn(Set<String> tags) {
        return !tags.isEmpty() ? tag.name.in(tags) : null;
    }
}
