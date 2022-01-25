package teamproject.lam_server.app.user.dto;

import lombok.Data;

@Data
public class LoginUserRequest {

    private String loginId;
    private String password;
}
