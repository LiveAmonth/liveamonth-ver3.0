package com.lam.liveamonthapp.domain.member.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GenderType implements EnumMapperType {
    MALE("남성"),
    FEMALE("여성");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
