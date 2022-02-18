package teamproject.lam_server.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import teamproject.lam_server.app.user.domain.User;

@Data
public class UserResponse {

    private String loginId;

    private String name;

    private String nickname;

    private String email;

    private String gender;

    private int age;

    public UserResponse(User user) {
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.gender = user.getGender().getValue();
        this.age = user.calcAge();
    }

    @Builder
    @Getter
    @AllArgsConstructor
    public static class TokenInfo{
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private Long refreshTokenExpirationTime;
    }
}
