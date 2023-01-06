package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.ALREADY_DROP_MEMBER;

public class AlreadyDropMember extends LiveamonthException {
    public AlreadyDropMember() {
        super(ALREADY_DROP_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_DROP_MEMBER;
    }
}
