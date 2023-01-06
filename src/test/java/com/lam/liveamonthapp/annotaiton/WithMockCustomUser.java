package com.lam.liveamonthapp.annotaiton;

import org.springframework.security.test.context.support.WithSecurityContext;
import com.lam.liveamonthapp.domain.member.constants.Role;

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
