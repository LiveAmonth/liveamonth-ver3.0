package teamproject.lam_server.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.entity.Member;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class OAuth2RegisterRequest {

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}")
    private String password;

    @NotBlank
    @Size(max = 20)
    private String nickname;

    @NotNull
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    @NotNull
    private GenderType gender;

    public Member toEntity(Member member, PasswordEncoder passwordEncoder) {
        member.registerBasicInfo(passwordEncoder.encode(password), nickname, birth, gender);
        return member;
    }
}
