package teamproject.lam_server.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import teamproject.lam_server.domain.member.constants.GenderType;

import java.time.LocalDate;

@Getter
public class OAuth2RegisterEditor {

    private final String password;

    private final String nickname;

    private final LocalDate birth;

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
