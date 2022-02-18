package teamproject.lam_server.app.user.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.LoginUserRequest;
import teamproject.lam_server.app.user.dto.UserResponse;
import teamproject.lam_server.app.user.repository.UserRepository;
import teamproject.lam_server.auth.JwtTokenProvider;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;

    /**
     * 1. authentication 생성
     * - request로 들어온 LoginId, Password로 Authentication 객체 생성
     * - authentication 는 인증 여부를 확인하는 authenticated = false
     *
     * 2. 검증
     * - 사용자, 비밀번호 체크
     * - authenticate -> CustomUserDetailService(loadUserByUsername)
     *
     * 3. 인증 정보를 기반으로 JWT토큰 생성
     */
    public User login(LoginUserRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        UserResponse.TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
        return userRepository.findByLoginId(request.getLoginId())
                .filter(m -> passwordEncoder.matches(request.getPassword(), m.getPassword()))
                .orElse(null);
    }

}
