package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DuplicateCheckResponse {
    private Boolean isAvailable;
    private String value;
    private String message;

    public static DuplicateCheckResponse of(boolean isAvailable, String value, String message) {
        return DuplicateCheckResponse.builder()
                .isAvailable(isAvailable)
                .value(value)
                .message(message)
                .build();
    }

}
