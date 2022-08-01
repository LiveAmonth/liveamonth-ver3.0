package teamproject.lam_server.exception.token;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.INVALID_TOKEN;

public class CustomInvalidToken extends LiveamonthException {
    public CustomInvalidToken() {
        super(INVALID_TOKEN.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return INVALID_TOKEN;
    }
}
