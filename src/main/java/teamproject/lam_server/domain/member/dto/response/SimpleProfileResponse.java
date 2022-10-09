package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

import static teamproject.lam_server.util.NumberUtil.countFormat;

@Getter
@Builder
public class SimpleProfileResponse {
    private Long id;
    private String loginId;
    private String nickname;
    private String image;
    private String numberOfReviews;
    private String numberOfSchedules;
    private String numberOfFollowers;
    private String numberOfFollows;

    public static SimpleProfileResponse of(Member member) {
        return SimpleProfileResponse.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .image(member.getImage())
                .numberOfReviews(countFormat(member.getNumberOfReviews()))
                .numberOfSchedules(countFormat(member.getNumberOfSchedules()))
                .numberOfFollowers(countFormat(member.getNumberOfFollowers()))
                .numberOfFollows(countFormat(member.getNumberOfFollows()))
                .build();
    }
}
