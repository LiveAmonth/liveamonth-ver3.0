package teamproject.lam_server.domain.member.dto.editor;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class ProfileEditor {

    @Size(max = 20)
    private final String nickname;

    @Pattern(regexp = "[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
    private final String email;

    private final String image;

    @Builder
    public ProfileEditor(String nickname, String email, String image) {
        this.nickname = nickname;
        this.email = email;
        this.image = image;
    }
}
