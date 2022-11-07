package teamproject.lam_server.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileEditor {

    private final String nickname;

    private final String email;

    @Builder
    public ProfileEditor(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }
}
