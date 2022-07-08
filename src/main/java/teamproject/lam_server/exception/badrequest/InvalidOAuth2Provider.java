package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.INVALID_OAUTH2_PROVIDER;

public class InvalidOAuth2Provider extends LiveamonthException {
    public InvalidOAuth2Provider() {
        super(INVALID_OAUTH2_PROVIDER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return INVALID_OAUTH2_PROVIDER;
    }
}
