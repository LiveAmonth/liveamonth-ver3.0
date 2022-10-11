package teamproject.lam_server.domain.review.repository;

import com.querydsl.core.BooleanBuilder;
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
import java.util.Set;

import static org.springframework.util.StringUtils.hasText;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewRepositoryImpl extends BasicRepository implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<Review> search(ReviewSearchCond cond, Pageable pageable) {
        List<Review> pageElements = getSearchElementsQuery(cond)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), Review.class, review))
                .groupBy(review.id)
                .fetch();

        JPAQuery<Long> countQuery = getSearchCountQuery(cond);

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

    private JPAQuery<Review> getSearchElementsQuery(ReviewSearchCond cond) {
        return queryFactory.selectFrom(review)
                .join(review.member, member).fetchJoin()
                .where(getSearchPredicts(cond));
    }


    private JPAQuery<Long> getSearchCountQuery(ReviewSearchCond cond) {
        return queryFactory.select(review.count())
                .from(review)
                .where(getSearchPredicts(cond));
    }

    private Predicate[] getSearchPredicts(ReviewSearchCond cond) {
        return new Predicate[]{
                reviewContains(cond.getSearchWord()),
                categoryIn(cond.getType()),
                tagContains(cond.getTags()),
                categoryEq(cond.getCategory())
        };
    }

    private BooleanExpression createdIdEq(String loginId) {
        return hasText(loginId) ? review.createdBy.eq(loginId) : null;
    }

    private BooleanExpression reviewIdLt(Long lastId) {
        return lastId != null ? review.id.lt(lastId) : null;
    }

    private BooleanExpression reviewContains(String word) {
        return hasText(word) ? review.title.contains(word).or(review.content.contains(word)).or(member.nickname.eq(word)) : null;
    }

    private BooleanExpression categoryIn(ReviewSearchType type) {
        return type != null ? review.category.in(type.getSubs()) : null;
    }

    private BooleanExpression categoryEq(ReviewCategory category) {
        return category != null ? review.category.eq(category) : null;
    }

    private BooleanBuilder tagContains(Set<String> condTags) {
        if (condTags == null || condTags.isEmpty()) return null;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        for (String condTag : condTags) {
            booleanBuilder.or(review.tags.contains(condTag));
        }
        return booleanBuilder;
    }
}
