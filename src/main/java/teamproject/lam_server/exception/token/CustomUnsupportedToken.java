package teamproject.lam_server.exception.token;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.UN_SUPPORTED_TOKEN;

public class CustomUnsupportedToken extends LiveamonthException {
    public CustomUnsupportedToken() {
        super(UN_SUPPORTED_TOKEN.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return UN_SUPPORTED_TOKEN;
    }
}
