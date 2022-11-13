package teamproject.lam_server.global.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BooleanCheckResponse {
    private boolean result;
    private String message;

    public static BooleanCheckResponse of(boolean result, String message) {
        return BooleanCheckResponse.builder()
                .result(result)
                .message(message)
                .build();
    }
}
