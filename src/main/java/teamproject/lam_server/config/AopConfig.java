package teamproject.lam_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import teamproject.lam_server.aop.LogTraceAspect;
import teamproject.lam_server.aop.trace.logtrace.LogTrace;

@Configuration
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace) {
        return new LogTraceAspect(logTrace);
    }
}
