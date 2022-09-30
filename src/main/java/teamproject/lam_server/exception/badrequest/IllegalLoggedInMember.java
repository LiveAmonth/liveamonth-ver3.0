package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.ILLEGAL_LOGGED_IN_MEMBER;

public class IllegalLoggedInMember extends LiveamonthException {
    public IllegalLoggedInMember() {
        super(ILLEGAL_LOGGED_IN_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ILLEGAL_LOGGED_IN_MEMBER;
    }
}
