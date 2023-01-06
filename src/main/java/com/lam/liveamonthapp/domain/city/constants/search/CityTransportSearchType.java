package com.lam.liveamonthapp.domain.city.constants.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.domain.city.constants.CityName;
import com.lam.liveamonthapp.domain.city.constants.TransportCategory;
import com.lam.liveamonthapp.global.constants.search.SearchConditionType;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CityTransportSearchType implements SearchConditionType {
    CITY_NAME("도시 이름", CityName.values()),
    TRANSPORT_CATEGORY("교통 수단", TransportCategory.values());
    private final String value;
    private final EnumMapperType[] conditionTypes;

    @Override
    public String getCode() {
        return name();
    }
}
