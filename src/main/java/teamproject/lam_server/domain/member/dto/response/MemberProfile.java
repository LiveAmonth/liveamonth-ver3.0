package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class MemberProfile {
    private String LoginId;
    private String nickname;
    private String image;


    public static MemberProfile of(Member member) {
        return MemberProfile.builder()
                .LoginId(member.getLoginId())
                .nickname(member.getNickname())
                .image(member.getImage())
                .build();
    }
}
