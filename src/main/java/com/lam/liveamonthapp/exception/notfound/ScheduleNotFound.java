package com.lam.liveamonthapp.exception.notfound;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.SCHEDULE_NOT_FOUND;

public class ScheduleNotFound extends LiveamonthException {

    public ScheduleNotFound() {
        super(SCHEDULE_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return SCHEDULE_NOT_FOUND;
    }
}
