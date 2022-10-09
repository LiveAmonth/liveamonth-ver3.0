package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.entity.Member;

import java.time.LocalDate;

import static teamproject.lam_server.util.NumberUtil.countFormat;

@Getter
@Builder
public class MemberProfileResponse {
    private Long id;
    private String loginId;
    private String nickname;
    private String image;
    private String name;
    private String email;
    private GenderType gender;
    private LocalDate birth;
    private String numberOfReviews;
    private String numberOfSchedules;
    private String numberOfFollowers;
    private String numberOfFollows;


    public static MemberProfileResponse of(Member member) {
        return MemberProfileResponse.builder()
                .id(member.getId())
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .image(member.getImage())
                .name(member.getName())
                .email(member.getEmail())
                .gender(member.getGender())
                .birth(member.getBirth())
                .numberOfReviews(countFormat(member.getNumberOfReviews()))
                .numberOfSchedules(countFormat(member.getNumberOfSchedules()))
                .numberOfFollowers(countFormat(member.getNumberOfFollowers()))
                .numberOfFollows(countFormat(member.getNumberOfFollows()))
                .build();
    }
}
