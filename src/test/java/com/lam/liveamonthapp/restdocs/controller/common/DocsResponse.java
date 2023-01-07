package com.lam.liveamonthapp.restdocs.controller.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class DocsResponse<T> {
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timeStamp;
    private String message;
    private T data;

    @Builder(builderClassName = "ResponseBuilder", builderMethodName = "ResponseBuilder")
    public DocsResponse(T data) {
        this.status = HttpStatus.OK.value();
        this.timeStamp = LocalDateTime.now();
        this.message = "테스트 응답 객체";
        this.data = data;
    }

    public static <T> DocsResponse<T> success(String message, T data) {
        return DocsResponse.<T>ResponseBuilder()
                .data(data)
                .build();
    }

}
