package teamproject.lam_server.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;

import java.util.Collections;

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
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        return new User(member.getLoginId(), member.getPassword(), Collections.singleton(grantedAuthority));
    }
}
