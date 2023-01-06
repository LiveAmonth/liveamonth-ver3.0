package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.ILLEGAL_OWNER_OF_POST;

public class IllegalWriterOfPost extends LiveamonthException {
    public IllegalWriterOfPost() {
        super(ILLEGAL_OWNER_OF_POST.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ILLEGAL_OWNER_OF_POST;
    }
}
