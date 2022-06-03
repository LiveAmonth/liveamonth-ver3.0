package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class FindIdResponse {
    private String loginId;

    public static FindIdResponse of(Member member) {
        return FindIdResponse.builder().loginId(member.getLoginId()).build();
    }
}

