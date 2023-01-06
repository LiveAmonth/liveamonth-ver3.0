package com.lam.liveamonthapp.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckResponse {
    private Boolean isAvailable;
    private String value;
    private String message;

    public static CheckResponse of(boolean isAvailable, String value, String message) {
        return CheckResponse.builder()
                .isAvailable(isAvailable)
                .value(value)
                .message(message)
                .build();
    }

}
