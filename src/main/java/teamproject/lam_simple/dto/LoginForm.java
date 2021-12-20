package teamproject.lam_simple.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.Assert;
import teamproject.lam_simple.constants.CategoryConstants;

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
