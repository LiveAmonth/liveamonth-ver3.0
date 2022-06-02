package teamproject.lam_server.domain.city.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.city.constants.CityInfoCategory;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;
import teamproject.lam_server.domain.city.constants.TransportCategory;
import teamproject.lam_server.domain.city.dto.condition.CityInfoSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityTransportSearchCond;
import teamproject.lam_server.domain.city.dto.condition.CityWeatherSearchCond;
import teamproject.lam_server.domain.city.dto.response.CityGridDataResponse;
import teamproject.lam_server.domain.city.dto.view.QCityGridDataResponse;
import teamproject.lam_server.domain.city.entity.CityInfo;
import teamproject.lam_server.domain.city.entity.CityTransport;
import teamproject.lam_server.domain.city.entity.CityWeather;
import teamproject.lam_server.domain.city.entity.QCity;
import teamproject.lam_server.util.BasicRepositoryUtil;

import java.util.List;

import static org.springframework.data.support.PageableExecutionUtils.getPage;
import static teamproject.lam_server.domain.city.entity.QCityInfo.cityInfo;
import static teamproject.lam_server.domain.city.entity.QCityTransport.cityTransport;
import static teamproject.lam_server.domain.city.entity.QCityWeather.cityWeather;

@Repository
@RequiredArgsConstructor
public class CityQueryRepository extends BasicRepositoryUtil {
    private final JPAQueryFactory queryFactory;

    public Page<CityInfo> searchInfo(CityInfoSearchCond cond, Pageable pageable) {
        List<CityInfo> content = getSearchInfoElementsQuery(cond)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(mapToOrderSpec(pageable.getSort(), CityInfo.class, cityInfo))
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

    private JPAQuery<CityInfo> getSearchInfoElementsQuery(CityInfoSearchCond cond) {
        return queryFactory.selectFrom(cityInfo)
                .where(
                        nameEq(cityInfo._super, cond.getName()),
                        infoCatEq(cond.getCategory())
                );
    }

    private JPAQuery<Long> getSearchInfoCountQuery(CityInfoSearchCond cond) {
        return count(queryFactory, cityInfo,
                nameEq(cityInfo._super, cond.getName()),
                infoCatEq(cond.getCategory())
        );
    }

    private JPAQuery<CityTransport> getSearchTransportElementsQuery(CityTransportSearchCond cond) {
        return queryFactory.selectFrom(cityTransport)
                .where(
                        nameEq(cityTransport._super, cond.getName()),
                        cityTransportCatEq(cond.getCategory()),
                        numGoe(cityTransport.stationCount, cond.getCountGoe()),
                        numLoe(cityTransport.stationCount, cond.getCountLoe())
                );
    }

    private JPAQuery<Long> getSearchTransportCountQuery(CityTransportSearchCond cond) {
        return count(queryFactory, cityInfo,
                nameEq(cityTransport._super, cond.getName()),
                cityTransportCatEq(cond.getCategory()),
                numGoe(cityTransport.stationCount, cond.getCountGoe()),
                numLoe(cityTransport.stationCount, cond.getCountLoe())
        );
    }

    private JPAQuery<CityWeather> getSearchWeatherElementsQuery(CityWeatherSearchCond cond) {
        return queryFactory.selectFrom(cityWeather)
                .where(
                        nameEq(cityWeather._super, cond.getName()),
                        cityMonthEq(cond.getMonth()),
                        numGoe(cityWeather.averageDegree, cond.getDegreeGoe()),
                        numLoe(cityWeather.averageDegree, cond.getDegreeLoe())
                );
    }

    private JPAQuery<Long> getSearchWeatherCountQuery(CityWeatherSearchCond cond) {
        return count(queryFactory, cityWeather,
                nameEq(cityWeather._super, cond.getName()),
                cityMonthEq(cond.getMonth()),
                numGoe(cityWeather.averageDegree, cond.getDegreeGoe()),
                numLoe(cityWeather.averageDegree, cond.getDegreeLoe()));
    }

    public List<CityGridDataResponse> findCityGridInfo(CityInfoCategory category, MonthCategory month) {
        return queryFactory
                .select(new QCityGridDataResponse(cityInfo.name,
                        cityInfo.image,
                        cityWeather.averageDegree,
                        cityTransport.score.sum().as("transportScore")))
                .from(cityInfo)
                .leftJoin(cityWeather).on(
                        joinCityNameEq(cityWeather.name),
                        cityMonthEq(month))
                .leftJoin(cityTransport).on(
                        joinCityNameEq(cityTransport.name))
                .where(
                        infoCatEq(category)
                )
                .groupBy(cityTransport.name)
                .fetch();
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

    private BooleanExpression joinNameEq() {
        return cityInfo.name.eq(cityWeather.name).and(cityInfo.name.eq(cityTransport.name));
    }

    private <T extends QCity> BooleanExpression nameEq(T t, CityName name) {
        return name != null ? t.name.eq(name) : null;
    }

    private BooleanExpression infoCatEq(CityInfoCategory category) {
        return category != null ? cityInfo.cityInfoCat.eq(category) : null;
    }

    private BooleanExpression cityMonthEq(MonthCategory month) {
        return month != null ? cityWeather.month.eq(month) : null;
    }

    private BooleanExpression cityTransportCatEq(TransportCategory category) {
        return category != null ? cityTransport.cityTransportCat.eq(category) : null;
    }
}
