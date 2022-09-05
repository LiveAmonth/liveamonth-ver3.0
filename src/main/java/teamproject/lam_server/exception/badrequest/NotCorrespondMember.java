package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.NOT_CORRESPOND_MEMBER;

public class NotCorrespondMember extends LiveamonthException {
    public NotCorrespondMember() {
        super(NOT_CORRESPOND_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return NOT_CORRESPOND_MEMBER;
    }
}
