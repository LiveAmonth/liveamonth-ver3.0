package teamproject.lam_server.app.city.repository.query;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.app.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.app.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.app.city.dto.view.CityGridDataResponse;
import teamproject.lam_server.app.city.dto.view.QCityGridDataResponse;
import teamproject.lam_server.app.city.entity.*;
import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.constants.CategoryConstants.Month;

import java.util.List;

import static org.springframework.data.support.PageableExecutionUtils.*;
import static org.springframework.util.StringUtils.hasText;
import static teamproject.lam_server.app.city.entity.QCityInfo.cityInfo;
import static teamproject.lam_server.app.city.entity.QCityTransport.cityTransport;
import static teamproject.lam_server.app.city.entity.QCityWeather.cityWeather;
import static teamproject.lam_server.constants.CategoryConstants.*;
import static teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;

@Repository
@RequiredArgsConstructor
public class CityQueryRepository {
    private final JPAQueryFactory queryFactory;

    public Page<CityInfo> searchCityInfos(CityInfoSearchCond cond, Pageable pageable) {
        List<CityInfo> content = queryFactory.selectFrom(cityInfo)
                .where(
                        cityNameEq(cityInfo._super, cond.getName()),
                        cityInfoCatEq(cond.getCategory()),
                        cityImageLike(cond.getImageExtension())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery =
                count(cityInfo, cityNameEq(cityInfo._super, cond.getName()),
                        cityInfoCatEq(cond.getCategory()),
                        cityImageLike(cond.getImageExtension()));

        return getPage(content, pageable, countQuery::fetchOne);
    }

    public Page<CityTransport> searchCityTransports(CityTransportSearchCond cond, Pageable pageable) {
        List<CityTransport> content = queryFactory.selectFrom(cityTransport)
                .where(
                        cityNameEq(cityTransport._super, cond.getName()),
                        cityTransportCatEq(cond.getCategory()),
                        numGoe(cityTransport.stationCount, cond.getCountGoe()),
                        numLoe(cityTransport.stationCount, cond.getCountLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery =
                count(cityTransport,
                        cityNameEq(cityTransport._super, cond.getName()),
                        cityTransportCatEq(cond.getCategory()),
                        numGoe(cityTransport.stationCount, cond.getCountGoe()),
                        numLoe(cityTransport.stationCount, cond.getCountLoe()));
        return getPage(content, pageable, countQuery::fetchOne);
    }

    public Page<CityWeather> searchCityWeather(CityWeatherSearchCond cond, Pageable pageable) {
        List<CityWeather> content = queryFactory.selectFrom(cityWeather)
                .where(
                        cityNameEq(cityWeather._super, cond.getName()),
                        cityMonthEq(cond.getMonth()),
                        numGoe(cityWeather.averageDegree, cond.getDegreeGoe()),
                        numLoe(cityWeather.averageDegree, cond.getDegreeLoe())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery =
                count(cityWeather,
                        cityNameEq(cityWeather._super, cond.getName()),
                        cityMonthEq(cond.getMonth()),
                        numGoe(cityWeather.averageDegree, cond.getDegreeGoe()),
                        numLoe(cityWeather.averageDegree, cond.getDegreeLoe()));
        return getPage(content, pageable, countQuery::fetchOne);
    }

    public List<CityGridDataResponse> findCityGridInfo(CityInfoCategory category, Month month) {
        return queryFactory
                .select(new QCityGridDataResponse(cityInfo.name,
                        cityInfo.image,
                        cityWeather.averageDegree,
                        cityTransport.stationCount.multiply(cityTransport.score).sum().as("transportScore")))
                .from(cityInfo)
                .leftJoin(cityWeather).on(
                        joinCityNameEq(cityWeather.name),
                        cityMonthEq(month))
                .leftJoin(cityTransport).on(
                        joinCityNameEq(cityTransport.name))
                .where(cityInfoCatEq(category))
                .groupBy(cityTransport.name)
                .fetch();
    }

    private <T extends EntityPathBase> JPAQuery<Long> count(T t, Predicate... predicate) {
        return (JPAQuery<Long>) queryFactory.select(t.count()).from(t).where(predicate);
    }

    private BooleanExpression numGoe(NumberPath numberPath, Number numGoe) {
        return numGoe != null ? numberPath.goe(numGoe) : null;
    }

    private BooleanExpression numLoe(NumberPath numberPath, Number numLoe) {
        return numLoe != null ? numberPath.loe(numLoe) : null;
    }

    private BooleanExpression joinCityNameEq(EnumPath<CityName> comp) {
        return comp != null ? cityInfo.name.eq(comp) : null;
    }

    private <T extends QCity> BooleanExpression cityNameEq(T t, CityName name) {
        return name != null ? t.name.eq(name) : null;
    }

    private BooleanExpression cityInfoCatEq(CityInfoCategory category) {
        return category != null ? cityInfo.cityInfoCat.eq(category) : null;
    }

    private BooleanExpression cityImageLike(String imageExtension) {
        return hasText(imageExtension) ? cityInfo.image.like("%" + imageExtension) : null;
    }

    private BooleanExpression cityMonthEq(Month month) {
        return month != null ? cityWeather.month.eq(month) : null;
    }

    private BooleanExpression cityTransportCatEq(TransportCategory category) {
        return category != null ? cityTransport.cityTransportCat.eq(category) : null;
    }
}
