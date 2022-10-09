package teamproject.lam_server.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;
import teamproject.lam_server.domain.interaction.entity.member.Follower;

import java.util.List;
import java.util.stream.Collectors;

import static teamproject.lam_server.util.NumberUtil.countFormat;

@Getter
@Builder
public class FollowerResponse {

    private String count;
    private List<MemberProfileResponse> followers;

    public static FollowerResponse of(List<Follower> followers) {
        return FollowerResponse.builder()
                .count(countFormat(followers.size()))
                .followers(followers.stream().map(follower -> MemberProfileResponse.of(follower.getFrom())).collect(Collectors.toList()))
                .build();
    }
}
