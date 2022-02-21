package teamproject.lam_server.app.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.member.dto.login.LoginMemberRequest;
import teamproject.lam_server.app.member.dto.login.LogoutMemberRequest;
import teamproject.lam_server.app.member.dto.login.ReissueTokenRequest;
import teamproject.lam_server.app.member.dto.login.TokenResponse;
import teamproject.lam_server.app.member.exception.CorrespondException;
import teamproject.lam_server.app.member.exception.UserNotFoundException;
import teamproject.lam_server.app.member.exception.ValidTokenException;
import teamproject.lam_server.app.member.repository.MemberRepository;
import teamproject.lam_server.auth.JwtTokenProvider;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LoginServiceImpl {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
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
        TokenResponse tokenResponse = jwtTokenProvider.generateToken(authentication);

        // RefreshToken Redis 저장, expirationTime -> 자동 삭제
        System.out.println("authentication = " + authentication);
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(),
                        tokenResponse.getRefreshToken(),
                        tokenResponse.getRefreshTokenExpirationTime(),
                        TimeUnit.MILLISECONDS);
        return tokenResponse;
    }

    public TokenResponse reissue(ReissueTokenRequest request) {
        // Refresh 토큰 검증
        this.checkValidateToken(request.getRefreshToken());

        // 토큰에서 회원 정보 가져오기(login id)
        Authentication authentication = getUserNameByAuthentication(request.getAccessToken());

        String key = this.getKey(authentication);
        if (!key.equals(request.getRefreshToken())) {
            throw new CorrespondException();
        }

        // 새로운 토큰 생성
        TokenResponse tokenResponse = jwtTokenProvider.generateToken(authentication);

        // Refresh 토큰 업데이트
        this.updateToken(key, tokenResponse.getRefreshToken(), tokenResponse.getRefreshTokenExpirationTime());

        return tokenResponse;
    }

    public void logout(LogoutMemberRequest request) {
        log.info("request={}", request);
        // Access 토큰 검증
        this.checkValidateToken(request.getAccessToken());
        System.out.println(" 검증완료 ");

        // 토큰에서 회원 정보 가져오기(login id)
        Authentication authentication = getUserNameByAuthentication(request.getAccessToken());

        // 위의 정보(loginId)로 저장된 refresh token 이 redis 에 있을 경우 삭제
        String key = this.getKey(authentication);
        if (redisTemplate.opsForValue().get(key) != null) {
            redisTemplate.delete(this.getKey(authentication));
        }

        // Access 토큰의 유효시간을 가지고 와서 블랙리스트로 저장
        Long expiration = jwtTokenProvider.getExpiration(request.getAccessToken());

        this.updateToken(request.getAccessToken(), "logout", expiration);
    }

    private void updateToken(String key, String refreshToken, Long refreshTokenExpirationTime) {
        redisTemplate.opsForValue().set(
                key,
                refreshToken,
                refreshTokenExpirationTime,
                TimeUnit.MILLISECONDS);
    }

    private Authentication getUserNameByAuthentication(String token) {
        return jwtTokenProvider.getAuthentication(token);
    }

    private void checkValidateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new ValidTokenException();
        }
    }

    private String getKey(Authentication authentication) {
        return "RT:" + authentication.getName();
    }
}
