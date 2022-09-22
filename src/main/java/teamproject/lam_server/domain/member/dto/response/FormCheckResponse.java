package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FormCheckResponse {
    private Boolean isAvailable;
    private String value;
    private String message;

    public static FormCheckResponse of(boolean isAvailable, String value, String message) {
        return FormCheckResponse.builder()
                .isAvailable(isAvailable)
                .value(value)
                .message(message)
                .build();
    }

}
