package teamproject.lam_server.mail.dto;

import lombok.Builder;
import lombok.Data;
import teamproject.lam_server.domain.member.entity.Member;

@Data
@Builder
public class TempPasswordSendMailInfo {
    private String name;
    private String email;
    private String password;

    public static TempPasswordSendMailInfo of(Member member) {
        return TempPasswordSendMailInfo.builder()
                .name(member.getName())
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
