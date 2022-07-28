package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.ALREADY_DROP_MEMBER;
import static teamproject.lam_server.exception.ErrorCode.ALREADY_FOLLOW_MEMBER;

public class AlreadyFollowMember extends LiveamonthException {
    public AlreadyFollowMember() {
        super(ALREADY_FOLLOW_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_FOLLOW_MEMBER;
    }
}
