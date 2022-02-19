package teamproject.lam_server.app.member.dto.login;

import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class LoginUserRequest {

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]{3,20}")
    private String loginId;

    @NotBlank
    private String password;

    public UsernamePasswordAuthenticationToken toAuthentication(){
        return new UsernamePasswordAuthenticationToken(
                loginId, password
        );
    }
}
