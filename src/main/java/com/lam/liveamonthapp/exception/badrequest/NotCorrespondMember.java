package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.NOT_CORRESPOND_MEMBER;

public class NotCorrespondMember extends LiveamonthException {
    public NotCorrespondMember() {
        super(NOT_CORRESPOND_MEMBER.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return NOT_CORRESPOND_MEMBER;
    }
}
