package com.lam.liveamonthapp.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagResponse {
    private String name;

    public static TagResponse of(String tag) {
        return TagResponse.builder()
                .name(tag)
                .build();
    }
}
