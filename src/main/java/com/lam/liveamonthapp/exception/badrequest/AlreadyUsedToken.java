package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.ALREADY_USED_TOKEN;

public class AlreadyUsedToken extends LiveamonthException {

    public AlreadyUsedToken() {
        super(ALREADY_USED_TOKEN.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_USED_TOKEN;
    }
}
