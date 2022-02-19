package teamproject.lam_server.app.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import teamproject.lam_server.app.member.domain.Member;
import teamproject.lam_server.app.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return memberRepository.findByLoginId(username)
                .map(this::createUserDetails)
                .orElseThrow(()->new UsernameNotFoundException("유저 없음"));
    }

    private UserDetails createUserDetails(Member member) {
        return new User(member.getLoginId(), member.getPassword(), member.getAuthorities());
    }
}
