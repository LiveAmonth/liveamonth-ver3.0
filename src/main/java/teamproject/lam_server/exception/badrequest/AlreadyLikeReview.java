package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.ALREADY_LIKE_REVIEW;

public class AlreadyLikeReview extends LiveamonthException {
    public AlreadyLikeReview() {
        super(ALREADY_LIKE_REVIEW.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_LIKE_REVIEW;
    }
}
