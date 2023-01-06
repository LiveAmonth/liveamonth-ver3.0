package com.lam.liveamonthapp.exception.notfound;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.MEMBER_NOT_FOUND;

public class MemberNotFound extends LiveamonthException {

    public MemberNotFound() {
        super(MEMBER_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return MEMBER_NOT_FOUND;
    }
}
