package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.ALREADY_DISLIKE_COMMENT;

public class AlreadyDislikeComment extends LiveamonthException {
    public AlreadyDislikeComment() {
        super(ALREADY_DISLIKE_COMMENT.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_DISLIKE_COMMENT;
    }
}
