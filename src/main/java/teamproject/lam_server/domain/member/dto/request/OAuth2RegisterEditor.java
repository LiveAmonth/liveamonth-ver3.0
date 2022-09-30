package teamproject.lam_server.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import teamproject.lam_server.domain.member.constants.GenderType;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
public class OAuth2RegisterEditor {

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}")
    private final String password;

    @NotBlank
    @Size(max = 20)
    private final String nickname;

    @NotNull
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birth;

    @NotNull
    private final GenderType gender;

    @Builder
    public OAuth2RegisterEditor(String password, String nickname, LocalDate birth, GenderType gender) {
        this.password = password;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
    }

    public UsernamePasswordAuthenticationToken toAuthentication(String loginId){
        return new UsernamePasswordAuthenticationToken(
                loginId, password
        );
    }
}
