package teamproject.lam_server.app.member.dto;

import lombok.Data;
import teamproject.lam_server.app.member.domain.Member;

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
