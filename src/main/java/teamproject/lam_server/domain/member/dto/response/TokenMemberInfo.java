package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class TokenMemberInfo {
    private Long id;
    private String loginId;
    private String nickname;


    public static TokenMemberInfo of(Member member) {
        return TokenMemberInfo.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .build();
    }
}
