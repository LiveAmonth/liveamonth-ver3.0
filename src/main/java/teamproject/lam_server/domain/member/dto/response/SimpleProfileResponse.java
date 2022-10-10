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
    private long numberOfReviews;
    private long numberOfSchedules;
    private long numberOfFollowers;
    private long numberOfFollows;

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
