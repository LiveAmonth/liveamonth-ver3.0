package com.lam.liveamonthapp.exception.notfound;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.CITY_NOT_FOUND;

public class CityNotFound extends LiveamonthException {

    public CityNotFound() {
        super(CITY_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return CITY_NOT_FOUND;
    }
}
