package teamproject.lam_server.exception.token;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.EMPTY_TOKEN;

public class CustomEmptyToken extends LiveamonthException {
    public CustomEmptyToken() {
        super(EMPTY_TOKEN.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return EMPTY_TOKEN;
    }
}
