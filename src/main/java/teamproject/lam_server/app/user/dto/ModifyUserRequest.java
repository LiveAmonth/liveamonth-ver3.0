package teamproject.lam_server.app.user.dto;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.constants.CategoryConstants.GenderTypes;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ModifyUserRequest {

    @Size(max = 20)
    private String nickname;

    private String image;
}
