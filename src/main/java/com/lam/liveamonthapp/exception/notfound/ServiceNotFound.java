package com.lam.liveamonthapp.exception.notfound;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.SERVICE_NOT_FOUND;

public class ServiceNotFound extends LiveamonthException {

    public ServiceNotFound() {
        super(SERVICE_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return SERVICE_NOT_FOUND;
    }
}
