package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.INVALID_SORT_OPTION;

public class InvalidSortOption extends LiveamonthException {
    public InvalidSortOption() {
        super(INVALID_SORT_OPTION.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return INVALID_SORT_OPTION;
    }
}
