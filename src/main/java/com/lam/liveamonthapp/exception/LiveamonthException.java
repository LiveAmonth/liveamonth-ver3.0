package com.lam.liveamonthapp.exception;

public abstract class LiveamonthException extends RuntimeException {
    public LiveamonthException(String message) {
        super(message);
    }

    public LiveamonthException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract ErrorCode getErrorCode();
}
