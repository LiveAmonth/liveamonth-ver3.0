package com.lam.liveamonthapp.domain.city.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CityName implements EnumMapperType {
    SE("서울"),
    GN("강릉"),
    GJ("경주"),
    BS("부산"),
    YS("여수"),
    JJ("제주");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }

}
