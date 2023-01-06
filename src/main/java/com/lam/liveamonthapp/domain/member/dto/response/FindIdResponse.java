package com.lam.liveamonthapp.domain.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.lam.liveamonthapp.util.StringUtil.coverContent;

@Getter
public class FindIdResponse {
    private final String loginId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
    private final LocalDate created;

    public FindIdResponse(String loginId, LocalDateTime createdDate) {
        this.loginId = coverContent(loginId);
        this.created = createdDate.toLocalDate();
    }
}

