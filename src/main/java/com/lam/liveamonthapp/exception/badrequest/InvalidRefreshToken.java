package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.INVALID_REFRESH_TOKEN;

public class InvalidRefreshToken extends LiveamonthException {
    public InvalidRefreshToken() {
        super(INVALID_REFRESH_TOKEN.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return INVALID_REFRESH_TOKEN;
    }
}
