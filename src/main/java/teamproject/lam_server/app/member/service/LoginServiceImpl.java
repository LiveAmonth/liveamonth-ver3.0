package teamproject.lam_server.app.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.member.dto.login.ReissueTokenRequest;
import teamproject.lam_server.app.member.dto.login.LoginMemberRequest;
import teamproject.lam_server.app.member.dto.login.TokenResponse;
import teamproject.lam_server.app.member.exception.UserNotFoundException;
import teamproject.lam_server.app.member.repository.MemberRepository;
import teamproject.lam_server.auth.JwtTokenProvider;
import teamproject.lam_server.global.dto.Response;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl {
    private final Response response;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisTemplate redisTemplate;

    /**
     * 1. authentication 생성
     * - request로 들어온 LoginId, Password로 Authentication 객체 생성
     * - authentication 는 인증 여부를 확인하는 authenticated = false
     * <p>
     * 2. 검증
     * - 사용자, 비밀번호 체크
     * - authenticate -> CustomUserDetailService(loadUserByUsername)
     * <p>
     * 3. 인증 정보를 기반으로 JWT토큰 생성
     */
    public TokenResponse login(LoginMemberRequest request) {
        memberRepository.findByLoginId(request.getLoginId()).orElseThrow(UserNotFoundException::new);

        // LoginID/Pw 로 Authentication 객체 생성
        // authentication 은 인증 여부를 확인하는 authenticated -> false
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        // 비밀 번호 체크, authencicate method가 실행될 때 CustomUserDetailService
        // 에서 만든 loadUserByUsername 이 싱핼
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponse tokenInfo = jwtTokenProvider.generateToken(authentication);

        // RefreshToken Redis 저장, expirationTime -> 자동 삭제
        redisTemplate.opsForValue()
                .set("RT:"+authentication.getName(),
                        tokenInfo.getRefreshToken(),
                        tokenInfo.getAccessTokenExpirationTime(),
                        TimeUnit.MILLISECONDS);

        return tokenInfo;
    }
    public ResponseEntity<?> reissue(ReissueTokenRequest request) {
        return null;
    }

}
