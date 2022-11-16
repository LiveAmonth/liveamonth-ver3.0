package teamproject.lam_server.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.repository.query.MemberQueryRepository;
import teamproject.lam_server.domain.member.repository.core.MemberRepository;
import teamproject.lam_server.exception.badrequest.NotDropMember;
import teamproject.lam_server.global.dto.response.PostIdResponse;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public PostIdResponse createManager(MemberCreate create) {
        return PostIdResponse.of(memberRepository.save(create.toManagerEntity(passwordEncoder)).getId());
    }

    @Transactional
    public void delete(Long id) {
        Long queryCount = memberQueryRepository.cleanDeleteById(id);
        if (queryCount == 0) throw new NotDropMember();
    }
}
