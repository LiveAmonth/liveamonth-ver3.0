package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.ALREADY_DISLIKE_COMMENT;
import static teamproject.lam_server.exception.ErrorCode.ALREADY_LIKE_SCHEDULE;

public class AlreadyDislikeComment extends LiveamonthException {
    public AlreadyDislikeComment() {
        super(ALREADY_DISLIKE_COMMENT.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_DISLIKE_COMMENT;
    }
}
