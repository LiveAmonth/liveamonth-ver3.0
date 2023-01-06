package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.NOT_DROP_MEMBER;

public class NotDropMember extends LiveamonthException {
    public NotDropMember() {
        super(NOT_DROP_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return NOT_DROP_MEMBER;
    }
}
