package teamproject.lam_server.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* teamproject.lam_server.*.api.*.*(..))")
    public void allController(){}
    @Pointcut("execution(* teamproject.lam_server.*.service.*.*(..))")
    public void allService(){}
    @Pointcut("execution(* teamproject.lam_server.*.repository.*.*(..))")
    public void allRepository(){}
}
