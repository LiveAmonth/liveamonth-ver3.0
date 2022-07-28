package teamproject.lam_server.domain.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import teamproject.lam_server.domain.auth.dto.PrincipalDetails;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.notfound.MemberNotFound;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * email로 member를 찾은 뒤 UserDetails 객체로 매핑해 리턴
     *
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(loginId)
                .map(PrincipalDetails::new)
                .orElseThrow(MemberNotFound::new);
    }
}
