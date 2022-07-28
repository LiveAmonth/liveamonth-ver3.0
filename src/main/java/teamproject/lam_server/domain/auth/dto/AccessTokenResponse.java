package teamproject.lam_server.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenResponse {
    private String accessToken;
    private String grantType;

    public static AccessTokenResponse of(String accessToken) {
        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .grantType("Bearer")
                .build();
    }
}
