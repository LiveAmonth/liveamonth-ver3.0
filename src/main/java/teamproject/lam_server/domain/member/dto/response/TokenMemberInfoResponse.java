package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class TokenMemberInfoResponse {
    private Long id;
    private String loginId;
    private String nickname;


    public static TokenMemberInfoResponse of(Member member) {
        return TokenMemberInfoResponse.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .build();
    }
}
