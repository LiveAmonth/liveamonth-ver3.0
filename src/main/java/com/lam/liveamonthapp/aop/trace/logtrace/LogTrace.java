package com.lam.liveamonthapp.aop.trace.logtrace;


import com.lam.liveamonthapp.aop.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
