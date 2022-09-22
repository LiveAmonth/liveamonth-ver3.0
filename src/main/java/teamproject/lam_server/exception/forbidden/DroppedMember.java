package teamproject.lam_server.exception.forbidden;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.DROP_MEMBER;

public class DroppedMember extends LiveamonthException {

    public DroppedMember() {
        super(DROP_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return DROP_MEMBER;
    }
}
