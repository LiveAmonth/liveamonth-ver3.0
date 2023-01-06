package com.lam.liveamonthapp.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.util.JsonUtil;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitMemberService {
    private static final String MEMBER = "member";
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void initMemberData() {
        memberRepository.saveAll(
                JsonUtil.jsonArrayToList(MEMBER, MemberCreate.class).stream()
                        .map(memberCreate -> memberCreate.toEntity(passwordEncoder))
                        .collect(Collectors.toList()
                        )
        );
    }

}
