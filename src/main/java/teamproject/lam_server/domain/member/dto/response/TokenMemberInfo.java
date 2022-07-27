package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class TokenMemberInfo {
    private String LoginId;
    private String nickname;


    public static TokenMemberInfo of(Member member) {
        return TokenMemberInfo.builder()
                .LoginId(member.getLoginId())
                .nickname(member.getNickname())
                .build();
    }
}
