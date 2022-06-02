package teamproject.lam_server.global.dto;

import lombok.Data;
import teamproject.lam_server.domain.member.entity.Member;

@Data
public class ProfileCardResponse {

    private String nickname;

    private String email;

    private String gender;

    private int age;

    public ProfileCardResponse(Member member) {
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.gender = member.getGender().getCode();
        this.age = member.calcAge();
    }
}
