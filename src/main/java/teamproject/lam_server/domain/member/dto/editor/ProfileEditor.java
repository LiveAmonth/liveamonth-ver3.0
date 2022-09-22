package teamproject.lam_server.domain.member.dto.editor;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileEditor {

    @Size(max = 20)
    private String nickname;

    @Pattern(regexp = "[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
    private String email;

    private String image;

    @Builder
    public ProfileEditor(String nickname, String email, String image) {
        this.nickname = nickname;
        this.email = email;
        this.image = image;
    }
}
