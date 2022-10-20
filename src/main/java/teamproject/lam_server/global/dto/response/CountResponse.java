package teamproject.lam_server.global.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CountResponse {
    private Long counts;

    public static CountResponse of(Long counts) {
        return CountResponse.builder().counts(counts).build();
    }
}
