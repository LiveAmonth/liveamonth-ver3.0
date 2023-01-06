package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.ALREADY_FOLLOW_MEMBER;

public class AlreadyFollowMember extends LiveamonthException {
    public AlreadyFollowMember() {
        super(ALREADY_FOLLOW_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_FOLLOW_MEMBER;
    }
}
