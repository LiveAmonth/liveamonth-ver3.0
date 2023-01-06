package com.lam.liveamonthapp.domain.review.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewMenuGroup implements EnumMapperType {

    REVIEW("추천 & 후기"),
    ETC("소통");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
