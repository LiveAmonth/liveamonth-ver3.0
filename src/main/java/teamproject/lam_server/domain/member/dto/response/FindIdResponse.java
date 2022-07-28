package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.util.StringUtil;

@Getter
@Builder
public class FindIdResponse {
    private String loginId;

    public static FindIdResponse of(Member member) {
        return FindIdResponse.builder()
                .loginId(StringUtil.coverContent(member.getLoginId())).build();
    }

}

