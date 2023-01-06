package com.lam.liveamonthapp.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.domain.member.repository.query.MemberQueryRepository;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.exception.badrequest.NotDropMember;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public PostIdResponse createManager(MemberCreate create) {
        return PostIdResponse.of(memberRepository.save(create.toManagerEntity(passwordEncoder)).getId());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Long queryCount = memberQueryRepository.cleanDeleteById(id);
        if (queryCount == 0) throw new NotDropMember();
    }
}
