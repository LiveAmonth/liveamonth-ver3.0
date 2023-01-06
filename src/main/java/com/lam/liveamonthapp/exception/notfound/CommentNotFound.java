package com.lam.liveamonthapp.exception.notfound;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.COMMENT_NOT_FOUND;

public class CommentNotFound extends LiveamonthException {

    public CommentNotFound() {
        super(COMMENT_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return COMMENT_NOT_FOUND;
    }
}
