package com.lam.liveamonthapp.domain.city.repository.query;

import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.MonthCategory;
import com.lam.liveamonthapp.domain.city.dto.response.api.CityGridDataResponse;
import com.lam.liveamonthapp.global.repository.BasicRepository;
import com.lam.liveamonthapp.global.repository.OrderByNull;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lam.liveamonthapp.domain.city.constants.CityIntroCategory.INTRO;
import static com.lam.liveamonthapp.domain.city.entity.QCityIntro.cityIntro;
import static com.lam.liveamonthapp.domain.city.entity.QCityTransport.cityTransport;
import static com.lam.liveamonthapp.domain.city.entity.QCityWeather.cityWeather;

@Repository
@RequiredArgsConstructor
public class CityQueryRepository extends BasicRepository {
    private final JPAQueryFactory queryFactory;

    public List<CityGridDataResponse> getCitySummaryInfo(MonthCategory month) {
        return queryFactory
                .select(Projections.constructor(CityGridDataResponse.class,
                        cityIntro.name,
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
                        infoCatEq(INTRO)
                )
                .groupBy(cityTransport.name)
                .orderBy(OrderByNull.DEFAULT)
                .fetch();
    }

    private BooleanExpression joinCityNameEq(EnumPath<CityName> comp) {
        return comp != null ? cityIntro.name.eq(comp) : null;
    }

    private BooleanExpression infoCatEq(CityIntroCategory category) {
        return category != null ? cityIntro.cityInfoCat.eq(category) : null;
    }

    private BooleanExpression cityMonthEq(MonthCategory month) {
        return month != null ? cityWeather.month.eq(month) : null;
    }
}
