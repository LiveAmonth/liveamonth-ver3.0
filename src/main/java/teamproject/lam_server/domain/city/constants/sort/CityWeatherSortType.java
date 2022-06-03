package teamproject.lam_server.domain.city.constants.sort;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.paging.metaModel.MetaModelType;

import static teamproject.lam_server.domain.city.entity.QCityWeather.cityWeather;
import static teamproject.lam_server.paging.metaModel.MetaModelUtil.getColumn;

@Getter
@AllArgsConstructor
public enum CityWeatherSortType implements MetaModelType {
    CITY_NAME_ASC("도시 이름순(ㄱ-ㅎ)", "name,asc", getColumn(cityWeather.name)),
    CITY_NAME_DESC("도시 이름순(ㅎ-ㄱ)", "name,desc", getColumn(cityWeather.name)),
    MONTH_ASC("달력순(1월-12월)", "month,asc", getColumn(cityWeather.month)),
    MONTH_DESC("달력순(12월-1월)", "month,desc", getColumn(cityWeather.month)),
    MIN_DEGREE_ASC("최저 기온 낮은순", "min-degree,asc", getColumn(cityWeather.minDegree)),
    MIN_DEGREE_DESC("최저 기온 높은순", "min-degree,desc", getColumn(cityWeather.minDegree)),
    AVERAGE_DEGREE_ASC("평균 기온 낮은순", "avg-degree,asc", getColumn(cityWeather.averageDegree)),
    AVERAGE_DEGREE_DESC("평균 기온 높은순", "avg-degree,desc", getColumn(cityWeather.averageDegree)),
    MAX_DEGREE_ASC("최고 기온 낮은순", "max-degree,asc", getColumn(cityWeather.maxDegree)),
    MAX_DEGREE_DESC("최고 기온 높은순", "max-degree,desc", getColumn(cityWeather.maxDegree));

    private String value;
    private String title;
    private String metaData;

    @Override
    public String getCode() {
        return title;
    }
}
