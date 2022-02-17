package teamproject.lam_server.app.user.dto;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Data
public class LoginUserRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(
                loginId, password
        );
    }
}
