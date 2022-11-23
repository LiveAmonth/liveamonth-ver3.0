package teamproject.lam_server.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SimpleProfileResponse {
    private Long id;
    private String loginId;
    private String nickname;
    private String image;
    private long numberOfReviews;
    private long numberOfSchedules;
    private long numberOfFollowers;
    private long numberOfFollows;
}
