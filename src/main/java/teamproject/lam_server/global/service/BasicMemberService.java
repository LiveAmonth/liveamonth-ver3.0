package teamproject.lam_server.global.service;

import lombok.RequiredArgsConstructor;
import teamproject.lam_server.auth.jwt.JwtTokenProvider;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.notfound.MemberNotFound;

@RequiredArgsConstructor
public abstract class BasicMemberService {

    protected final MemberRepository memberRepository;
    protected final JwtTokenProvider jwtTokenProvider;

    protected Member getMemberByToken(String token) {
        return memberRepository.findByLoginId(
                        jwtTokenProvider.getAuthentication(token).getName()
                )
                .orElseThrow(MemberNotFound::new);
    }
}
