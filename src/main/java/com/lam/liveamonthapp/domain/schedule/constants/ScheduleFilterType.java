package com.lam.liveamonthapp.domain.schedule.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ScheduleFilterType implements EnumMapperType {
    CITY_NAME("도시 이름"),
    START_DATE("시작 날짜");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
