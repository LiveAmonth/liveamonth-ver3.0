package com.lam.liveamonthapp.domain.city.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MonthCategory implements EnumMapperType {
    JAN("1월"),
    FEB("2월"),
    MAR("3월"),
    APR("4월"),
    MAY("5월"),
    JUN("6월"),
    JUL("7월"),
    AUG("8월"),
    SEP("9월"),
    OCT("10월"),
    NOV("11월"),
    DEC("12월");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }

}
