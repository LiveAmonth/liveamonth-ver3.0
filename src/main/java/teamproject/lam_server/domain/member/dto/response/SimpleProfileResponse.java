package teamproject.lam_server.domain.member.dto.response;

import lombok.Getter;

import static teamproject.lam_server.constants.AttrConstants.IMAGEBB_URL;

@Getter
public class SimpleProfileResponse {
    private final Long id;
    private final String loginId;
    private final String nickname;
    private final String image;
    private final long numberOfReviews;
    private final long numberOfSchedules;
    private final long numberOfFollowers;
    private final long numberOfFollows;

    public SimpleProfileResponse(Long id, String loginId, String nickname, String image, long numberOfReviews, long numberOfSchedules, long numberOfFollowers, long numberOfFollows) {
        this.id = id;
        this.loginId = loginId;
        this.nickname = nickname;
        this.image = IMAGEBB_URL + image;
        this.numberOfReviews = numberOfReviews;
        this.numberOfSchedules = numberOfSchedules;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollows = numberOfFollows;
    }
}
