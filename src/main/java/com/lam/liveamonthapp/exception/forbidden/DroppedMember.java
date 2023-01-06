package com.lam.liveamonthapp.exception.forbidden;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.DROP_MEMBER;

public class DroppedMember extends LiveamonthException {

    public DroppedMember() {
        super(DROP_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return DROP_MEMBER;
    }
}
