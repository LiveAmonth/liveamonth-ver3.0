package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.ALREADY_LIKE_COMMENT;

public class AlreadyLikeComment extends LiveamonthException {
    public AlreadyLikeComment() {
        super(ALREADY_LIKE_COMMENT.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_LIKE_COMMENT;
    }
}
