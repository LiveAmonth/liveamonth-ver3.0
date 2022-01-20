package teamproject.lam_server.app.city.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.app.city.dto.view.CityGridDataResponse;
import teamproject.lam_server.app.city.dto.QCityGridDataResponse;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.constants.CategoryConstants.Month;

import java.util.List;
import java.util.Optional;

import static teamproject.lam_server.app.city.entity.QCityInfo.cityInfo;
import static teamproject.lam_server.app.city.entity.QCityTransport.cityTransport;
import static teamproject.lam_server.app.city.entity.QCityWeather.cityWeather;
import static teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;

@Repository
@RequiredArgsConstructor
public class CityQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<CityGridDataResponse> findCityGridInfo(CityInfoCategory category, Month month) {
        return queryFactory
                .select(new QCityGridDataResponse(cityInfo.name,
                        cityInfo.image,
                        cityWeather.averageDegree,
                        cityTransport.station_count.multiply(cityTransport.score).sum().as("transportScore")))
                .from(cityInfo)
                .leftJoin(cityWeather).on(
                        joinCityNameEq(cityWeather.name),
                        currentMonthEq(month))
                .leftJoin(cityTransport).on(
                        joinCityNameEq(cityTransport.name))
                .where(cityInfoCatEq(category))
                .groupBy(cityTransport.name)
                .fetch();
    }

    public List<CityInfo> findCityInfo(CityName name, CityInfoCategory cityInfoCat){
        return queryFactory
                .selectFrom(cityInfo)
                .where(
                        cityInfoNameEq(name),
                        cityInfoCatEq(cityInfoCat)
                )
                .fetch();
    }
    public Optional<CityInfo> findOneCityInfo(CityName name, CityInfoCategory cityInfoCat){
        return Optional.ofNullable(queryFactory
                .selectFrom(cityInfo)
                .where(
                        cityInfoNameEq(name),
                        cityInfoCatEq(cityInfoCat)
                )
                .fetchOne());
    }

    private BooleanExpression joinCityNameEq(EnumPath<CityName> comp) {
        return comp != null ? cityInfo.name.eq(comp) : null;
    }

    private BooleanExpression cityInfoNameEq(CityName name) {
        return name != null ? cityInfo.name.eq(name) : null;
    }
    private BooleanExpression cityInfoCatEq(CityInfoCategory category) {
        return category != null ? cityInfo.cityInfoCat.eq(category) : null;
    }

    private BooleanExpression currentMonthEq(Month month) {
        return cityWeather.month.eq(month);
    }
}
