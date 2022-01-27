package teamproject.lam_server.app.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
