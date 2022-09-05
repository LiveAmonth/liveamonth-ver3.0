package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import teamproject.lam_server.domain.member.dto.request.SignUpRequest;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.util.JsonUtil;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitMemberService {
    private static final String MEMBER = "member";
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void initMemberData() {
        memberRepository.saveAll(
                JsonUtil.jsonArrayToList(MEMBER, SignUpRequest.class).stream()
                        .map(signUpRequest -> signUpRequest.toEntity(passwordEncoder))
                        .collect(Collectors.toList()
                        )
        );
    }

}
