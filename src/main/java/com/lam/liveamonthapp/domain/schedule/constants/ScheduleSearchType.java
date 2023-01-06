package com.lam.liveamonthapp.domain.schedule.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ScheduleSearchType implements EnumMapperType {
    MEMBER_NICKNAME("작성자"),
    TITLE("제목");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
