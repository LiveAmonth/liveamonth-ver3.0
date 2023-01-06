package com.lam.liveamonthapp.domain.review.constants;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReviewCategory implements EnumMapperType {

    SE_REVIEW("서울"),
    GN_REVIEW("강릉"),
    GJ_REVIEW("경주"),
    BS_REVIEW("부산"),
    YS_REVIEW("여수"),
    JJ_REVIEW("제주"),
    OTHER("다른 지역 후기"),
    QUESTION("질문 게시판"),
    FREE("자유 게시판");

    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
