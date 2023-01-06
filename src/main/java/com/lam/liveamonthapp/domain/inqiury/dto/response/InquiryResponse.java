package com.lam.liveamonthapp.domain.inqiury.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.lam.liveamonthapp.domain.inqiury.constants.InquiryCategory;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class InquiryResponse {
    private Long id;
    private String title;
    private String content;
    private InquiryCategory category;
    private boolean isAnswered;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;
    private InquiryAnswerResponse answer;
}
