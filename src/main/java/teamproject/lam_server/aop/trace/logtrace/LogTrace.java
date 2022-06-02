package teamproject.lam_server.aop.trace.logtrace;


import teamproject.lam_server.aop.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
