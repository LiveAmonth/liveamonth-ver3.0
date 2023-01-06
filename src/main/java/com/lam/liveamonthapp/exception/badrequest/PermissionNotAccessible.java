package com.lam.liveamonthapp.exception.badrequest;

import com.lam.liveamonthapp.exception.ErrorCode;
import com.lam.liveamonthapp.exception.LiveamonthException;

import static com.lam.liveamonthapp.exception.ErrorCode.PERMISSION_NOT_ACCESSIBLE;

public class PermissionNotAccessible extends LiveamonthException {
    public PermissionNotAccessible() {
        super(PERMISSION_NOT_ACCESSIBLE.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return PERMISSION_NOT_ACCESSIBLE;
    }
}
