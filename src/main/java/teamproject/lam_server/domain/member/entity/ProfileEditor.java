package teamproject.lam_server.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileEditor {

    private final String nickname;

    private final String email;

    private final String image;

    @Builder
    public ProfileEditor(String nickname, String email, String image) {
        this.nickname = nickname;
        this.email = email;
        this.image = image;
    }
}
