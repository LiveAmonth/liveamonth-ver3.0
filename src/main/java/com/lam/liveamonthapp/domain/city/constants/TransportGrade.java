package com.lam.liveamonthapp.domain.city.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TransportGrade implements EnumMapperType {
    GOOD("좋음"),
    FAIR("보통"),
    BAD("나쁨");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }

}
