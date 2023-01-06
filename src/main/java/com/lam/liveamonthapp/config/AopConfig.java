package com.lam.liveamonthapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lam.liveamonthapp.aop.LogTraceAspect;
import com.lam.liveamonthapp.aop.trace.logtrace.LogTrace;
import com.lam.liveamonthapp.aop.trace.logtrace.ThreadLocalLogTrace;

@Configuration
public class AopConfig {
    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
