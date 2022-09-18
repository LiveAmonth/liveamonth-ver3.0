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
    private int numberOfReviews;
    private int numberOfSchedules;
    private int numberOfFollowers;
    private int numberOfFollows;

    public static SimpleProfileResponse of(Member member) {
        return SimpleProfileResponse.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .image(member.getImage())
                .numberOfReviews(member.getNumberOfReviews())
                .numberOfSchedules(member.getNumberOfSchedules())
                .numberOfFollowers(member.getNumberOfFollowers())
                .numberOfFollows(member.getNumberOfFollows())
                .build();
    }
}
