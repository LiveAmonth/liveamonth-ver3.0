package teamproject.lam_server.global.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;

@Component
public class Response {

    @Getter
    @Builder
    private static class Body{
        private int status;
        private String result;
        private String message;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-EEE HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime timeStamp; //요청 시간
        private Object data;
        private Object error;
    }

    public ResponseEntity<?> success(Object data, String message, HttpStatus status) {
        return ResponseEntity.ok(
                Body.builder()
                        .status(status.value())
                        .data(data)
                        .result("ok")
                        .error(Collections.emptyList())
                        .message(message)
                        .timeStamp(LocalDateTime.now())
                        .build());
    }

    public ResponseEntity<?> success() {
        return this.success(Collections.emptyList(), null, HttpStatus.OK);
    }
    public ResponseEntity<?> success(String message) {
        return this.success(Collections.emptyList(), message, HttpStatus.OK);
    }
    public ResponseEntity<?> success(Object data) {
        return this.success(data, null, HttpStatus.OK);
    }
    public ResponseEntity<?> fail(Object data, String msg, HttpStatus status) {
        Body body = Body.builder()
                .status(status.value())
                .data(data)
                .result("fail")
                .message(msg)
                .error(Collections.emptyList())
                .build();
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<?> fail(String msg, HttpStatus status) {
        return fail(Collections.emptyList(), msg, status);
    }

    public ResponseEntity<?> invalidFields(LinkedList<LinkedHashMap<String, String>> errors) {
        Body body = Body.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .data(Collections.emptyList())
                .result("fail")
                .message("")
                .error(errors)
                .build();
        return ResponseEntity.ok(body);
    }
}
