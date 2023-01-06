package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.ALREADY_LIKE_REVIEW;

public class AlreadyLikeReview extends LiveamonthException {
    public AlreadyLikeReview() {
        super(ALREADY_LIKE_REVIEW.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_LIKE_REVIEW;
    }
}
