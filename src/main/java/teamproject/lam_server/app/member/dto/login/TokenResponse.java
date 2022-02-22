package teamproject.lam_server.app.member.dto.login;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.app.member.domain.Member;
import teamproject.lam_server.app.member.dto.MemberResponse;

@Getter
@AllArgsConstructor
@Builder
public class TokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;
}
