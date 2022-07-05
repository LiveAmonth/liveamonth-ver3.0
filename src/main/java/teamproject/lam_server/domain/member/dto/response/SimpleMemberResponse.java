package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class SimpleMemberResponse {
    private String LoginId;
    private String nickName;
    private String image;

    public static SimpleMemberResponse of(Member member) {
        return SimpleMemberResponse.builder()
                .LoginId(member.getLoginId())
                .nickName(member.getNickname())
                .image(member.getImage())
                .build();
    }
}
