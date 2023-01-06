package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.ILLEGAL_LOGGED_IN_MEMBER;

public class IllegalLoggedInMember extends LiveamonthException {
    public IllegalLoggedInMember() {
        super(ILLEGAL_LOGGED_IN_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ILLEGAL_LOGGED_IN_MEMBER;
    }
}
