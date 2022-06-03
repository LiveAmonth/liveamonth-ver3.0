package teamproject.lam_server.domain.city.constants.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.constants.TransportCategory;
import teamproject.lam_server.global.constants.search.SearchConditionType;
import teamproject.lam_server.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CityTransportSearchType implements SearchConditionType {
    CITY_NAME("도시 이름", CityName.values()),
    TRANSPORT_CATEGORY("교통 수단", TransportCategory.values());
    private String value;
    private EnumMapperType[] conditionTypes;

    @Override
    public String getCode() {
        return name();
    }
}
