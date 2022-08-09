package teamproject.lam_server.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class ScheduleProfileResponse {

    private String nickname;
    private String image;

    public static ScheduleProfileResponse of(Member member) {
        return ScheduleProfileResponse.builder()
                .nickname(member.getNickname())
                .image(member.getImage())
                .build();
    }
}
