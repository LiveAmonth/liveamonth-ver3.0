package teamproject.lam_server.annotaiton;

import org.springframework.security.test.context.support.WithSecurityContext;
import teamproject.lam_server.domain.member.constants.Role;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String loginId() default "customLoginId";
    String password() default "customPassword1!";
    String name() default "customName";
    String nickname() default "customNickname";
    String email() default "customEmail@liveamonth.com";

    Role role() default Role.USER;
}
