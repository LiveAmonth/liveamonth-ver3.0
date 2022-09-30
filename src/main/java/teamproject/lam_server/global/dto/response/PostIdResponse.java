package teamproject.lam_server.global.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostIdResponse {
    private Long id;

    public static PostIdResponse of(Long id) {
        return PostIdResponse.builder().id(id).build();
    }
}
