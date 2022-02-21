package teamproject.lam_server.app.member.dto.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LogoutMemberRequest {

    @NotEmpty
    private String accessToken;

    @NotEmpty
    private String refreshToken;
}
