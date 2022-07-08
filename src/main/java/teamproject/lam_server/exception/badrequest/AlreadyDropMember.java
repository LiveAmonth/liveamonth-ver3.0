package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.ALREADY_DROP_MEMBER;

public class AlreadyDropMember extends LiveamonthException {
    public AlreadyDropMember() {
        super(ALREADY_DROP_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_DROP_MEMBER;
    }
}
