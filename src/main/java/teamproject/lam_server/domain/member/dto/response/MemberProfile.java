package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.entity.Member;

import java.time.LocalDate;

@Getter
@Builder
public class MemberProfile {
    private String loginId;
    private String nickname;
    private String image;
    private String name;
    private String email;
    private GenderType gender;
    private LocalDate birth;
    private int numberOfReview;
    private int numberOfSchedule;
    private int numberOfFollower;


    public static MemberProfile of(Member member) {
        return MemberProfile.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickname())
                .image(member.getImage())
                .name(member.getName())
                .email(member.getEmail())
                .gender(member.getGender())
                .birth(member.getBirth())
                .numberOfReview(member.getReviews().size())
                .numberOfSchedule(member.getSchedules().size())
                .numberOfFollower(0)
                .build();
    }
}
