package com.lam.liveamonthapp.exception.notfound;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.REVIEW_NOT_FOUND;

public class ReviewNotFound extends LiveamonthException {

    public ReviewNotFound() {
        super(REVIEW_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return REVIEW_NOT_FOUND;
    }
}
