package teamproject.lam_server.domain.review.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.review.dto.condition.ReviewSearchCond;
import teamproject.lam_server.domain.review.entity.Review;
import teamproject.lam_server.global.repository.BasicRepository;

import java.util.List;

import static org.springframework.util.StringUtils.hasText;
import static teamproject.lam_server.domain.member.entity.QMember.member;
import static teamproject.lam_server.domain.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl extends BasicRepository implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<Review> search(ReviewSearchCond cond, Pageable pageable) {
        List<Review> elements = getSearchElementsQuery(cond)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), Review.class, review))
                .fetch();

        JPAQuery<Long> countQuery = getSearchCountQuery(cond);

        return PageableExecutionUtils.getPage(
                elements,
                pageable,
                countQuery::fetchOne);
    }

    private JPAQuery<Review> getSearchElementsQuery(ReviewSearchCond cond) {
        return queryFactory.selectFrom(review)
                .join(review.member, member).fetchJoin()
                .where(
                        titleContains(cond.getTitle()),
                        contentContains(cond.getContent()),
                        memberNicknameEq(cond.getWriter())
                );
    }


    private JPAQuery<Long> getSearchCountQuery(ReviewSearchCond cond) {
        return queryFactory.select(review.count())
                .from(review)
                .join(review.member, member).fetchJoin()
                .where(
                        titleContains(cond.getTitle()),
                        contentContains(cond.getContent()),
                        memberNicknameEq(cond.getWriter())
                );
    }

//    private Predicate[] getSearchPredicts(ReviewSearchCond cond) {
//        return new Predicate[]{
//                titleContains(cond.getTitle()),
//                contentContains(cond.getContent()),
//                memberNicknameContains(cond.getWriter())
//        };
//    }

    private BooleanExpression titleContains(String title) {
        return hasText(title) ? review.title.contains(title) : null;
    }

    private BooleanExpression contentContains(String content) {
        return hasText(content) ? review.content.contains(content) : null;
    }

    private BooleanExpression memberNicknameEq(String writer) {
        return hasText(writer) ? member.nickname.eq(writer) : null;
    }

}
