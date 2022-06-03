package teamproject.lam_server.domain.city.constants.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.MonthCategory;
import teamproject.lam_server.global.constants.search.SearchConditionType;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CityWeatherSearchType implements SearchConditionType {
    CITY_NAME("도시 이름", CityName.values()),
    MONTH("월", MonthCategory.values());
    private String value;
    private EnumMapperType[] conditionTypes;

    @Override
    public String getCode() {
        return name();
    }

}
