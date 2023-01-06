package com.lam.liveamonthapp.domain.city.repository.query;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;
import com.lam.liveamonthapp.domain.city.constants.TransportCategory;
import com.lam.liveamonthapp.domain.city.dto.condition.CityIntroSearchCond;
import com.lam.liveamonthapp.domain.city.dto.condition.CityTransportSearchCond;
import com.lam.liveamonthapp.domain.city.dto.condition.CityWeatherSearchCond;
import com.lam.liveamonthapp.domain.city.dto.response.api.CityGridDataResponse;
import com.lam.liveamonthapp.domain.city.entity.CityIntro;
import com.lam.liveamonthapp.domain.city.entity.CityTransport;
import com.lam.liveamonthapp.domain.city.entity.CityWeather;
import com.lam.liveamonthapp.domain.city.entity.QCity;
import com.lam.liveamonthapp.global.repository.BasicRepository;
import com.lam.liveamonthapp.global.repository.OrderByNull;

import java.util.List;

import static org.springframework.data.support.PageableExecutionUtils.getPage;
import static com.lam.liveamonthapp.domain.city.entity.QCityIntro.cityIntro;
import static com.lam.liveamonthapp.domain.city.entity.QCityTransport.cityTransport;
import static com.lam.liveamonthapp.domain.city.entity.QCityWeather.cityWeather;

@Repository
@RequiredArgsConstructor
public class CityQueryRepository extends BasicRepository {
    private final JPAQueryFactory queryFactory;

    public Page<CityIntro> searchIntro(CityIntroSearchCond cond, Pageable pageable) {
        List<CityIntro> content = getSearchInfoElementsQuery(cond)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), CityIntro.class, cityIntro))
                .fetch();

        JPAQuery<Long> countQuery = getSearchInfoCountQuery(cond);
        return getPage(content, pageable, countQuery::fetchOne);
    }

    public Page<CityTransport> searchTransport(CityTransportSearchCond cond, Pageable pageable) {
        List<CityTransport> content = getSearchTransportElementsQuery(cond)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), CityTransport.class, cityTransport))
                .fetch();

        JPAQuery<Long> countQuery = getSearchTransportCountQuery(cond);
        return getPage(content, pageable, countQuery::fetchOne);
    }
    public Page<CityWeather> searchWeather(CityWeatherSearchCond cond, Pageable pageable) {
        List<CityWeather> content = getSearchWeatherElementsQuery(cond)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), CityWeather.class, cityWeather))
                .fetch();

        JPAQuery<Long> countQuery = getSearchWeatherCountQuery(cond);
        return getPage(content, pageable, countQuery::fetchOne);
    }

    private JPAQuery<CityIntro> getSearchInfoElementsQuery(CityIntroSearchCond cond) {
        return queryFactory.selectFrom(cityIntro)
                .where(
                        nameEq(cityIntro._super, cond.getName()),
                        infoCatEq(cond.getCategory())
                );
    }

    private JPAQuery<Long> getSearchInfoCountQuery(CityIntroSearchCond cond) {
        return count(queryFactory, cityIntro,
                nameEq(cityIntro._super, cond.getName()),
                infoCatEq(cond.getCategory())
        );
    }

    private JPAQuery<CityTransport> getSearchTransportElementsQuery(CityTransportSearchCond cond) {
        return queryFactory.selectFrom(cityTransport)
                .where(
                        nameEq(cityTransport._super, cond.getName()),
                        cityTransportCatEq(cond.getCategory())
                );
    }

    private JPAQuery<Long> getSearchTransportCountQuery(CityTransportSearchCond cond) {
        return count(queryFactory, cityIntro,
                nameEq(cityTransport._super, cond.getName()),
                cityTransportCatEq(cond.getCategory())
        );
    }

    private JPAQuery<CityWeather> getSearchWeatherElementsQuery(CityWeatherSearchCond cond) {
        return queryFactory.selectFrom(cityWeather)
                .where(
                        nameEq(cityWeather._super, cond.getName()),
                        cityMonthEq(cond.getMonth())
                );
    }

    private JPAQuery<Long> getSearchWeatherCountQuery(CityWeatherSearchCond cond) {
        return count(queryFactory, cityWeather,
                nameEq(cityWeather._super, cond.getName()),
                cityMonthEq(cond.getMonth()));
    }

    public List<CityGridDataResponse> findCityGridInfo(CityIntroCategory category, MonthCategory month) {
        return queryFactory
                .select(Projections.constructor(CityGridDataResponse.class,cityIntro.name,
                        cityIntro.image,
                        cityWeather.averageDegree,
                        cityTransport.score.sum().as("transportScore")))
                .from(cityIntro)
                .leftJoin(cityWeather).on(
                        joinCityNameEq(cityWeather.name),
                        cityMonthEq(month))
                .leftJoin(cityTransport).on(
                        joinCityNameEq(cityTransport.name))
                .where(
                        infoCatEq(category)
                )
                .groupBy(cityTransport.name)
                .orderBy(OrderByNull.DEFAULT)
                .fetch();
    }

    private BooleanExpression joinCityNameEq(EnumPath<CityName> comp) {
        return comp != null ? cityIntro.name.eq(comp) : null;
    }

    private <T extends QCity> BooleanExpression nameEq(T t, CityName name) {
        return name != null ? t.name.eq(name) : null;
    }

    private BooleanExpression infoCatEq(CityIntroCategory category) {
        return category != null ? cityIntro.cityInfoCat.eq(category) : null;
    }

    private BooleanExpression cityMonthEq(MonthCategory month) {
        return month != null ? cityWeather.month.eq(month) : null;
    }

    private BooleanExpression cityTransportCatEq(TransportCategory category) {
        return category != null ? cityTransport.cityTransportCat.eq(category) : null;
    }
}
