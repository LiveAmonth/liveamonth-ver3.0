package teamproject.lam_server.app.user.dto;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.lam_server.constants.CategoryConstants.GenderTypes;
import teamproject.lam_server.app.user.domain.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class CreateUserRequest {

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]{3,20}")
    private String loginId;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z가-힣]{2,20}")
    private String name;

    @NotEmpty
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}")
    private String password;

    @NotEmpty
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}")
    private String passwordCheck;

    @NotEmpty
    @Size(max = 20)
    private String nickname;

    @NotEmpty
    @Size(max = 20)
    private String emailId;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")
    private String emailDomain;

    @NotNull
    private LocalDateTime birth;

    @NotNull
    private GenderTypes gender;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .loginId(this.loginId)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .nickname(this.nickname)
                .birth(this.birth)
                .email(this.emailId +"@"+this.emailDomain)
                .gender(this.gender)
                .build();
    }

}
