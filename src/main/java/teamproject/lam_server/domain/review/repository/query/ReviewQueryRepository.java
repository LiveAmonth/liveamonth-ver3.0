package teamproject.lam_server.domain.review.repository.query;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.review.constants.ReviewCategory;
import teamproject.lam_server.domain.review.constants.ReviewSearchType;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.global.repository.BasicRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;
import static teamproject.lam_server.domain.review.entity.QReviewTag.reviewTag;
import static teamproject.lam_server.domain.review.entity.QTag.tag;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewQueryRepository extends BasicRepository{
    private final JPAQueryFactory queryFactory;

    public Page<Review> search(ReviewSearchCond cond, List<Long> tagInReviewIds, Pageable pageable) {
        List<Review> pageElements = getSearchElementsQuery(cond, tagInReviewIds)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), Review.class, review))
                .groupBy(review.id)
                .distinct()
                .fetch();

        JPAQuery<Long> countQuery = getSearchCountQuery(cond, tagInReviewIds);

        return PageableExecutionUtils.getPage(
                pageElements,
                pageable,
                countQuery::fetchOne);
    }

    public List<Review> getReviewByMember(String loginId, Integer size, Long lastId) {
        return queryFactory.selectFrom(review)
                .leftJoin(review.member, member).fetchJoin()
                .where(
                        createdIdEq(loginId),
                        reviewIdLt(lastId)
                )
                .limit(size)
                .orderBy(review.id.desc())
                .fetch();
    }

    public Optional<Review> getReview(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(review)
                .join(review.member, member).fetchJoin()
                .where(reviewIdEq(id))
                .fetchOne());
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

    private JPAQuery<Review> getSearchElementsQuery(ReviewSearchCond cond, List<Long> reviewTagIds) {
        return queryFactory.selectFrom(review)
                .join(review.member, member).fetchJoin()
                .where(getSearchPredicts(cond, reviewTagIds));
    }


    private JPAQuery<Long> getSearchCountQuery(ReviewSearchCond cond, List<Long> reviewTagIds) {
        return queryFactory.select(review.count())
                .from(review)
                .join(review.member, member)
                .where(getSearchPredicts(cond, reviewTagIds));
    }

    private Predicate[] getSearchPredicts(ReviewSearchCond cond, List<Long> reviewTagIds) {
        return new Predicate[]{
                reviewSearchWordContains(cond.getSearchWord()),
                categoryIn(cond.getType()),
                tagContains(reviewTagIds),
                categoryEq(cond.getCategory())
        };
    }

    private BooleanExpression createdIdEq(String loginId) {
        return hasText(loginId) ? review.createdBy.eq(loginId) : null;
    }

    private BooleanExpression reviewIdEq(Long id) {
        return id != null ? review.id.eq(id) : null;
    }

    private BooleanExpression reviewIdLt(Long lastId) {
        return lastId != null ? review.id.lt(lastId) : null;
    }

    private BooleanExpression tittleContains(String word) {
        return hasText(word) ? review.title.contains(word): null;
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
}
