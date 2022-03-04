package teamproject.lam_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static teamproject.lam_server.constants.InterceptorConstants.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("http//localhost:8081")
                .allowCredentials(true);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * white list를 상수 클래스로 만들어 관리
         * 자바 8 이후 상수 클래스 정보를 리스트로 담는 방법
         * 위의 whitelist의 리스트 정보를 여러군데에서 사용한다면
         * 상수클래스로 관리하는 게 편리
         */
//        List<String> whitelist = Stream.of(Whitelist.values())
//                .map(Whitelist::getPath)
//                .collect(Collectors.toList());
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns(whitelist);
    }
}
