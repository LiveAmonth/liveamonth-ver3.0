package teamproject.lam_server.app.user.dto;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.constants.CategoryConstants.GenderTypes;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class CreateUserRequest {

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9]{3,20}")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z가-힣]{2,20}")
    private String name;

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}")
    private String password;

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}")
    private String passwordCheck;

    @NotBlank
    @Size(max = 20)
    private String nickname;

    @NotBlank
    @Size(max = 20)
    private String emailId;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")
    private String emailDomain;

    @NotNull
    @Past
    private LocalDate birth;

    @NotNull
    private GenderTypes gender;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .loginId(this.loginId)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .nickname(this.nickname)
                .birth(this.birth)
                .email(this.emailId + "@" + this.emailDomain)
                .gender(this.gender)
                .build();
    }

}
