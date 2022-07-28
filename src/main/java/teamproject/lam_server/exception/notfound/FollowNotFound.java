package teamproject.lam_server.exception.notfound;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.FOLLOW_NOT_FOUND;
import static teamproject.lam_server.exception.ErrorCode.MEMBER_NOT_FOUND;

public class FollowNotFound extends LiveamonthException {

    public FollowNotFound() {
        super(FOLLOW_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return FOLLOW_NOT_FOUND;
    }
}
