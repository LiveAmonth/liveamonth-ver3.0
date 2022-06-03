package teamproject.lam_server.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import teamproject.lam_server.auth.dto.PrincipalDetails;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.util.BasicServiceUtil;

import static teamproject.lam_server.global.exception.ErrorCode.MEMBER_NOT_FOUND;
import static teamproject.lam_server.util.BasicServiceUtil.getExceptionSupplier;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * email로 member를 찾은 뒤 UserDetails 객체로 매핑해 리턴
     *
     * @param email
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(loginId)
                .map(PrincipalDetails::new)
                .orElseThrow(getExceptionSupplier(MEMBER_NOT_FOUND));
    }
}
