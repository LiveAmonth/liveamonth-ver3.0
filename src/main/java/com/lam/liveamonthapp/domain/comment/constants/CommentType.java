package com.lam.liveamonthapp.domain.comment.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.global.enumMapper.EnumMapperType;

@Getter
@AllArgsConstructor
public enum CommentType implements EnumMapperType {
    SCHEDULE("스케줄 댓글"),
    REVIEW("후기 댓글");
    private final String value;

    @Override
    public String getCode() {
        return name();
    }
}
