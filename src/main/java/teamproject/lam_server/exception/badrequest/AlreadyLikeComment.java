package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.ALREADY_LIKE_COMMENT;

public class AlreadyLikeComment extends LiveamonthException {
    public AlreadyLikeComment() {
        super(ALREADY_LIKE_COMMENT.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_LIKE_COMMENT;
    }
}
