package com.lam.liveamonthapp.exception.notfound;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.FOLLOW_NOT_FOUND;

public class FollowNotFound extends LiveamonthException {

    public FollowNotFound() {
        super(FOLLOW_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return FOLLOW_NOT_FOUND;
    }
}
