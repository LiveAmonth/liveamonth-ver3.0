package com.lam.liveamonthapp.aop.pointcuts;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.lam.*.api.*.*(..))")
    public void allController(){}
    @Pointcut("execution(* com.lam.*.service.*.*(..))")
    public void allService(){}
    @Pointcut("execution(* com.lam.*.repository.*.*(..))")
    public void allRepository(){}
}
