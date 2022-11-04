package teamproject.lam_server.annotaiton;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import teamproject.lam_server.auth.jwt.CustomUserDetailsService;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.service.MemberService;

import java.time.LocalDate;

@RequiredArgsConstructor
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    private final MemberService memberService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        String loginId = customUser.loginId();
        String password = customUser.password();

        MemberCreate memberCreate = MemberCreate.builder()
                .loginId(customUser.loginId())
                .password(customUser.password())
                .name(customUser.name())
                .nickname(customUser.nickname())
                .email(customUser.email())
                .birth(LocalDate.now())
                .gender(GenderType.MALE)
                .build();
        memberService.signUp(memberCreate);

        UserDetails userDetails = userDetailsService.loadUserByUsername(customUser.loginId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails
                , userDetails.getPassword()
                , userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
