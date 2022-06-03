package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.constants.Role;
import teamproject.lam_server.domain.member.entity.Member;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginResponse {
    private Long id;
    private Role roles;
    private LocalDateTime createDateTime;

    public static LoginResponse of(Member member) {
        return LoginResponse.builder()
                .id(member.getId())
                .roles(member.getRole())
                .createDateTime(member.getCreatedDate())
                .build();
    }

}
