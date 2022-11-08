package teamproject.lam_server.domain.member.dto.request;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileEdit {

    @Size(max = 20)
    private String nickname;

    @Pattern(regexp = "[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
    private String email;

    @Builder
    public ProfileEdit(String nickname, String email, String image) {
        this.nickname = nickname;
        this.email = email;
    }
}
