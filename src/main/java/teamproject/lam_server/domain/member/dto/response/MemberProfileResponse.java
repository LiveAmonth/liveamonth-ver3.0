package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.entity.Member;

import java.time.LocalDate;

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
    private long numberOfReviews;
    private long numberOfSchedules;
    private long numberOfFollowers;
    private long numberOfFollows;


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
                .numberOfReviews(member.getNumberOfReviews())
                .numberOfSchedules(member.getNumberOfSchedules())
                .numberOfFollowers(member.getNumberOfFollowers())
                .numberOfFollows(member.getNumberOfFollows())
                .build();
    }
}
