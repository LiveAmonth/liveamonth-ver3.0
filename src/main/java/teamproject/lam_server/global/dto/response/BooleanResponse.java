package teamproject.lam_server.global.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BooleanResponse {
    private boolean result;

    public static BooleanResponse of(boolean result) {
        return BooleanResponse.builder()
                .result(result)
                .build();
    }
}
