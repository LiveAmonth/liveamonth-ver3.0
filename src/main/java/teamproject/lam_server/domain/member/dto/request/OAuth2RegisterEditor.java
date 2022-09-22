package teamproject.lam_server.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.member.constants.GenderType;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2RegisterEditor {

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

    @Builder
    public OAuth2RegisterEditor(String password, String nickname, LocalDate birth, GenderType gender) {
        this.password = password;
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
    }
}
