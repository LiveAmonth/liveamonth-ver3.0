package teamproject.lam_server.annotaiton;

import org.springframework.security.test.context.support.WithSecurityContext;
import teamproject.lam_server.domain.member.constants.Role;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {
    String loginId();
    String password() default "testPassword1!";
    String name() default "testName";
    String nickname() default "testNickname";
    String email() default "test@gmail.com";

    Role role();
}
