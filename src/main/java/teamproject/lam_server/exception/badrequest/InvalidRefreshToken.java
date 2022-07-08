package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.INVALID_REFRESH_TOKEN;

public class InvalidRefreshToken extends LiveamonthException {
    public InvalidRefreshToken() {
        super(INVALID_REFRESH_TOKEN.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return INVALID_REFRESH_TOKEN;
    }
}
