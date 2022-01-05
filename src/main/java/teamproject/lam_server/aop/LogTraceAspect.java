package teamproject.lam_server.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import teamproject.lam_server.trace.TraceStatus;
import teamproject.lam_server.trace.logtrace.LogTrace;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    @Pointcut("execution(* teamproject.lam_server..*(..))")
    public void allController(){}
    @Pointcut("execution(* teamproject.lam_server.*.service..*(..))")
    public void allService(){}
    @Pointcut("execution(* teamproject.lam_server.*.repository..*(..))")
    public void allRepository(){}

    @Around("execution(* teamproject.lam_server.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            // 로직 호출
            Object result = joinPoint.proceed();
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }

    }
}
