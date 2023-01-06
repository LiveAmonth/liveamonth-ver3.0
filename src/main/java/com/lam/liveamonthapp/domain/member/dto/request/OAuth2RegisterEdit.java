package com.lam.liveamonthapp.domain.member.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.lam.liveamonthapp.domain.member.constants.GenderType;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2RegisterEdit {

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

    public UsernamePasswordAuthenticationToken toAuthentication(String loginId){
        return new UsernamePasswordAuthenticationToken(
                loginId, password
        );
    }
}
