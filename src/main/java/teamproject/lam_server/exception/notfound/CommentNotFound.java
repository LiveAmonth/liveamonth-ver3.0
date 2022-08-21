package teamproject.lam_server.exception.notfound;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.COMMENT_NOT_FOUND;

public class CommentNotFound extends LiveamonthException {

    public CommentNotFound() {
        super(COMMENT_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return COMMENT_NOT_FOUND;
    }
}
