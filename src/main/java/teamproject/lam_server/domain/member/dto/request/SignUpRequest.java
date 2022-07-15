package teamproject.lam_server.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.entity.Member;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class SignUpRequest {

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z\\d]{3,20}")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,20}")
    private String password;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z가-힣]{2,20}")
    private String name;

    @NotBlank
    @Size(max = 20)
    private String nickname;

    @NotBlank
    @Pattern(regexp = "[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*")
    private String email;

    @NotNull
    @Past
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @NotNull
    private GenderType gender;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.basicBuilder()
                .loginId(this.loginId)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .nickname(this.nickname)
                .birth(this.birth)
                .email(this.email)
                .gender(this.gender)
                .build();
    }

}
