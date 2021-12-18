package teamproject.lam_simple.dto;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.lam_simple.constants.CategoryConstants.GenderTypes;
import teamproject.lam_simple.domain.User;

import javax.validation.constraints.*;
import java.sql.Date;

@Data
public class UserForm {

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
    private String email_id;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}")
    private String email_domain;

    @NotNull
    private Date birth;

    @NotNull
    private GenderTypes genderTypes;

    // => 비즈니스 로직
    public String unifyEmail(){return this.email_id+"@"+this.email_domain;}

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .loginId(this.loginId)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .nickname(this.nickname)
                .birth(this.birth)
                .email(this.unifyEmail())
                .genderTypes(this.genderTypes)
                .build();
    }

}
