package com.lam.liveamonthapp.exception.notfound;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.INQUIRY_NOT_FOUND;

public class InquiryNotFound extends LiveamonthException {

    public InquiryNotFound() {
        super(INQUIRY_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return INQUIRY_NOT_FOUND;
    }
}
