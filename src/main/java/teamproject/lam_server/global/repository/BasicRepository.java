package teamproject.lam_server.global.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import java.util.Collections;
import java.util.List;

import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;

@Slf4j
public abstract class BasicRepository {
    protected <T extends BaseTimeEntity, S extends EntityPathBase<T>> OrderSpecifier<?>[] mapToOrderSpec(Sort sort, Class<T> t, S s) {
        OrderSpecifier[] orderSpecifiers = sort.stream().map(
                order -> new OrderSpecifier(
                        order.getDirection().isAscending() ? ASC : DESC,
                        Expressions.path(t, s, order.getProperty()))
        ).toArray(OrderSpecifier[]::new);
        return orderSpecifiers;
    }

    protected <T extends EntityPathBase> JPAQuery<Long> count(JPAQueryFactory queryFactory, T t, Predicate... predicate) {
        return (JPAQuery<Long>) queryFactory.select(t.count()).from(t).where(predicate);
    }

    protected <T> List<T> fetchIndexingQuery(boolean isEmpty, JPAQuery<T> query) {
        return isEmpty ? Collections.emptyList() : query.fetch();
    }

}
