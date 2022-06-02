package teamproject.lam_server.domain.member.dto;

import lombok.Data;
import teamproject.lam_server.domain.member.entity.Member;

@Data
public class FindPasswordResponse {
    private String name;
    private String email;
    private String password;

    public FindPasswordResponse(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
        this.password = member.getPassword();
    }
}
