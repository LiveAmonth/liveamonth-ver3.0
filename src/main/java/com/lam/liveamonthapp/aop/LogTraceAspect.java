package com.lam.liveamonthapp.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import com.lam.liveamonthapp.aop.trace.TraceStatus;
import com.lam.liveamonthapp.aop.trace.logtrace.LogTrace;

@Slf4j
@Aspect
@RequiredArgsConstructor
public class LogTraceAspect {

    private final LogTrace logTrace;

    @Pointcut("execution(* com.lam..*(..))")
    public void allController(){}
    @Pointcut("execution(* com.lam.*.service..*(..))")
    public void allService(){}
    @Pointcut("execution(* com.lam.*.repository..*(..))")
    public void allRepository(){}

    @Around("execution(* com.lam.liveamonthapp.domain..*(..))")
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
