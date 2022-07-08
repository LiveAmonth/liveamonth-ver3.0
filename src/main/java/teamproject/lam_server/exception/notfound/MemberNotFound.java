package teamproject.lam_server.exception.notfound;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.MEMBER_NOT_FOUND;

public class MemberNotFound extends LiveamonthException {

    public MemberNotFound() {
        super(MEMBER_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return MEMBER_NOT_FOUND;
    }
}
