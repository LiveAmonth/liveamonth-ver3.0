package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

import java.util.List;

@Getter
@Builder
public class MemberInteractionResponse {

    private List<Long> followings;
    private List<Long> followers;
    private List<Long> schedules;
    private List<Long> scheduleComments;
    private List<Long> reviews;
    private List<Long> reviewComments;

    public static MemberInteractionResponse of(Member member) {
        return null;
    }
}
