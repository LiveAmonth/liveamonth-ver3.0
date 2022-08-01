package teamproject.lam_server.exception.token;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.EXPIRED_JWT;

public class CustomExpiredJwt extends LiveamonthException {
    public CustomExpiredJwt() {
        super(EXPIRED_JWT.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return EXPIRED_JWT;
    }
}
