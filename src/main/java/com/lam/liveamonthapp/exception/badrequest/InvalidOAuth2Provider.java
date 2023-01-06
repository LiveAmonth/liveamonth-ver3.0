package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.INVALID_OAUTH2_PROVIDER;

public class InvalidOAuth2Provider extends LiveamonthException {
    public InvalidOAuth2Provider() {
        super(INVALID_OAUTH2_PROVIDER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return INVALID_OAUTH2_PROVIDER;
    }
}
