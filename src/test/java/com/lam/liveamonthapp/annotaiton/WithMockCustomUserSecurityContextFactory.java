package com.lam.liveamonthapp.annotaiton;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.auth.dto.PrincipalDetails;
import com.lam.liveamonthapp.domain.member.constants.GenderType;
import com.lam.liveamonthapp.domain.member.constants.Role;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;

import java.time.LocalDate;

@RequiredArgsConstructor
@Transactional
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        Member member = memberRepository.findByLoginId(customUser.loginId()).orElseGet(
                () -> {
                    Role role = customUser.role();
                    MemberCreate memberCreate =
                            MemberCreate.builder()
                                    .loginId(customUser.loginId())
                                    .password(customUser.password())
                                    .name(customUser.name())
                                    .nickname(customUser.nickname())
                                    .email(customUser.email())
                                    .birth(LocalDate.now().minusDays(1))
                                    .gender(GenderType.MALE.name())
                                    .build();

                    return role == Role.USER
                            ? memberRepository.save(memberCreate.toEntity(passwordEncoder))
                            : memberRepository.save(memberCreate.toManagerEntity(passwordEncoder));
                }
        );

        UserDetails userDetails = new PrincipalDetails(member);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails
                , userDetails.getPassword()
                , userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
