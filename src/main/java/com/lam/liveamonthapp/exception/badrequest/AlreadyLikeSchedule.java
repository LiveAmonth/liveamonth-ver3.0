package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.ALREADY_LIKE_SCHEDULE;

public class AlreadyLikeSchedule extends LiveamonthException {
    public AlreadyLikeSchedule() {
        super(ALREADY_LIKE_SCHEDULE.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_LIKE_SCHEDULE;
    }
}
