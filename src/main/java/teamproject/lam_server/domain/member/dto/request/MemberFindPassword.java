package teamproject.lam_server.domain.member.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberFindPassword {

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]{3,20}")
    private String loginId;

    @NotNull
    @Pattern(regexp = "[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*")
    private String email;

    @Builder
    public MemberFindPassword(String loginId, String email) {
        this.loginId = loginId;
        this.email = email;
    }
}
