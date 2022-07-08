package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.NOT_DROP_MEMBER;

public class NotDropMember extends LiveamonthException {
    public NotDropMember() {
        super(NOT_DROP_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return NOT_DROP_MEMBER;
    }
}
