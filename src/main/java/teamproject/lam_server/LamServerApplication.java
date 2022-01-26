package teamproject.lam_server;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import teamproject.lam_server.config.AopConfig;
import teamproject.lam_server.trace.logtrace.LogTrace;
import teamproject.lam_server.trace.logtrace.ThreadLocalLogTrace;

import javax.persistence.EntityManager;
import java.util.Locale;

@SpringBootApplication
@Import(AopConfig.class)
@EnableJpaAuditing
public class LamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LamServerApplication.class, args);
    }

    @Bean
    public LogTrace logTrace(){
        return new ThreadLocalLogTrace();
    }

    @Bean
    public JPAQueryFactory queryFactory(EntityManager em){
        return new JPAQueryFactory(em);
    }

    @Bean
    public LocaleResolver localResolver(){
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.KOREA);
        return resolver;
    }
}
