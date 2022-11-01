package teamproject.lam_server.global.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.ValidationResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static teamproject.lam_server.exception.ErrorCode.ARGUMENTS_NOT_VALID;
import static teamproject.lam_server.global.constants.ResponseMessage.SUCCESS;

@Component
public class CustomResponse {
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private static class Result<T> {
        private int status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime timeStamp;
        private String message;
        private T data;
        private String error;

        @Builder(builderClassName = "ResponseBuilder", builderMethodName = "ResponseBuilder")
        public Result(int status, LocalDateTime timeStamp, String message, T data) {
            this.status = status;
            this.timeStamp = timeStamp;
            this.message = message;
            this.data = data;
        }

        @Builder(builderClassName = "ErrorBuilder", builderMethodName = "ErrorBuilder")
        private Result(ErrorCode errorCode) {
            this.timeStamp = LocalDateTime.now();
            this.status = errorCode.getStatusCode();
            this.error = errorCode.name();
            this.message = errorCode.getMessage();
        }

        @Builder(builderClassName = "ValidationBuilder", builderMethodName = "ValidationBuilder")
        private Result(ErrorCode errorCode, T data) {
            this.timeStamp = LocalDateTime.now();
            this.status = errorCode.getStatusCode();
            this.error = errorCode.name();
            this.message = errorCode.getMessage();
            this.data = data;
        }
    }

    public static <T> ResponseEntity<?> success(HttpStatus status, String message, T data) {
        return ResponseEntity
                .status(status.value())
                .body(Result.<T>ResponseBuilder()
                        .timeStamp(LocalDateTime.now())
                        .status(status.value())
                        .message(message)
                        .data(data)
                        .build());
    }

    /**
     * 상태 리턴
     *
     * @return status
     */
    public static ResponseEntity<?> success() {
        return success(HttpStatus.OK, SUCCESS, Collections.emptyList());
    }

    /**
     * 메시지, 상태 리턴
     *
     * @return message, status
     */
    public static ResponseEntity<?> success(String message) {
        return success(HttpStatus.OK, message, Collections.emptyList());
    }

    /**
     * 데이터, 상태 리턴
     *
     * @return data, status
     */
    public static <T> ResponseEntity<?> success(T data) {
        return success(HttpStatus.OK, SUCCESS, data);
    }

    /**
     * 데이터, 메시지 리턴
     *
     * @return data, message
     */
    public static <T> ResponseEntity<?> success(String message, T data) {
        return success(HttpStatus.OK, message, data);
    }

    public static ResponseEntity<Object> fail(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(Result.ErrorBuilder()
                        .errorCode(errorCode)
                        .build()
                );
    }
    public static Result<?> failToJson(ErrorCode errorCode) {
        return Result.ErrorBuilder()
                .errorCode(errorCode)
                .build();
    }

    public static ResponseEntity<Object> validationFail(List<ValidationResponse> detail) {
        return ResponseEntity
                .status(ARGUMENTS_NOT_VALID.getStatusCode())
                .body(Result.ValidationBuilder()
                        .errorCode(ARGUMENTS_NOT_VALID)
                        .data(detail)
                        .build()
                );

    }

    public static void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getStatusCode());
        response.getWriter().print(new Gson().toJson(CustomResponse.failToJson(errorCode)));
    }
}
