package teamproject.lam_server.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

    @Builder
    public LoginForm(String loginId) {
        this.loginId = loginId;
    }

}
