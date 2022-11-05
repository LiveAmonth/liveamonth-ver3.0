package teamproject.lam_server.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.badrequest.NotDropMember;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAdminServiceImpl implements MemberAdminService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createManager(MemberCreate create) {
        memberRepository.save(create.toManagerEntity(passwordEncoder));
    }

    @Transactional
    public void delete(Long id) {
        Long queryCount = memberRepository.cleanDeleteById(id);
        if (queryCount == 0) throw new NotDropMember();
    }
}
