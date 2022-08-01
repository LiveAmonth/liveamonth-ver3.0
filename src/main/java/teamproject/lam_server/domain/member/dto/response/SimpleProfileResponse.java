package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class SimpleProfileResponse {
    private Long id;
    private String loginId;
    private String nickname;
    private int numOfReviews;
    private int numOfSchedules;
    private int numOfFollowers;

    public static SimpleProfileResponse of(Member member) {
        return SimpleProfileResponse.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .numOfReviews(member.getReviews().size())
                .numOfSchedules(member.getSchedules().size())
                .numOfFollowers(member.getFollowers().size())
                .build();
    }
}
