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
    private String image;
    private int numOfReviews;
    private int numOfSchedules;
    private int numOfFollowers;

    public static SimpleProfileResponse of(Member member) {
        return SimpleProfileResponse.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .image(member.getImage())
                .numOfReviews(member.getReviewCount())
                .numOfSchedules(member.getScheduleCount())
                .numOfFollowers(member.getFollowersCount())
                .build();
    }
}
