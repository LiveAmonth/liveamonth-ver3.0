package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.member.entity.Member;

@Getter
@Builder
public class PostCountResponse {

    private int numOfReviews;
    private int numOfSchedules;
    private int numOfFollowers;

    public static PostCountResponse of(Member member){
        return PostCountResponse.builder()
                .numOfReviews(member.getReviews().size())
                .numOfSchedules(member.getSchedules().size())
                .numOfFollowers(member.getFollowers().size())
                .build();
    }
}
