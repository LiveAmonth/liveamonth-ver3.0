package teamproject.lam_server.domain.member.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberReconfirm {

    @NotBlank
    private String password;

    @Builder
    public MemberReconfirm(String password) {
        this.password = password;
    }
}
