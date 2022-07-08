package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.ALREADY_USED_TOKEN;

public class AlreadyUsedToken extends LiveamonthException {

    public AlreadyUsedToken() {
        super(ALREADY_USED_TOKEN.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_USED_TOKEN;
    }
}
