package teamproject.lam_server.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.auth.dto.PrincipalDetails;
import teamproject.lam_server.auth.dto.TokenResponse;
import teamproject.lam_server.auth.jwt.JwtTokenProvider;
import teamproject.lam_server.domain.member.dto.request.LoginRequest;
import teamproject.lam_server.domain.member.dto.request.OAuth2RegisterRequest;
import teamproject.lam_server.domain.member.dto.response.MemberProfileResponse;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.badrequest.AlreadyUsedToken;
import teamproject.lam_server.exception.badrequest.InvalidRefreshToken;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.redis.RedisRepository;

import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthServiceImpl implements AuthService {
    private static final String BLACK_LIST_VALUE = "LOGOUT_TOKEN";

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisRepository redisRepository;
    private final PasswordEncoder passwordEncoder;

    public TokenResponse login(LoginRequest request) {
        // request로 온 email을 가지고 해당 회원이 있는지 확인
        if (!memberRepository.existsByLoginId(request.getLoginId()))
            throw new MemberNotFound();

        // email, pw 로 Authentication 객체 생성 -> email password // request
        UsernamePasswordAuthenticationToken authenticationToken = request.toAuthentication();

        /*
          AuthenticationManagerBuilder 의 authenticate 메서드가 호출되면
          CustomUserDetailService 에서 override 한 loadUserByUsername 이 호출됨.
          loadUserByUsername 으로 가져온 UserDetails 객체를 가지고 password check 를 한다.
          response -> Member(role...)
         */
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        // 인증 정보를 기반으로 토큰(access, refresh, expiration) 생성
        TokenResponse tokenResponse = jwtTokenProvider.generateToken(authentication);

        // RefreshToken Redis 저장, expirationTime 이 지나면 자동 삭제
        redisRepository.save(jwtTokenProvider.getRefreshTokenKey(authentication), tokenResponse);
        return tokenResponse;
    }

    public TokenResponse reissue(String refreshTokenRequest) {
        // Refresh 토큰 검증
        if (isInvalidationToken(refreshTokenRequest))
            throw new InvalidRefreshToken();

        // Security Context 에서 Authentication 객체 가져오기 :: email
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Redis 에서 'RT:'+email 을 key 값으로 하는 value 를 가져옴
        String key = jwtTokenProvider.getRefreshTokenKey(authentication);
        String savedRefreshToken = redisRepository.getValue(key);

        // logout 되어 Redis 에 refreshToken 이 없는 경우 체크
        if (!hasText(savedRefreshToken))
            throw new AlreadyUsedToken();

        // Redis 에 저장되어 있는 RefreshToken 정보와 request 의 RefreshToken 정보 비교
        if (!savedRefreshToken.equals(refreshTokenRequest))
            throw new InvalidRefreshToken();

        // 새로운 토큰 생성 -> access만 재발급 하는걸로
        String accessToken = jwtTokenProvider.createAccessToken(authentication, new Date());

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenRequest)
                .build();
    }

    public void logout(String accessTokenRequest) {
        // JwtAuthenticationFilter 에서 doFilter 메서드를 통해 securityContext 에 들어있는 Authentication 객체를 가져옴.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 위의 정보(loginId)로 저장된 refresh token 이 redis 에 있을 경우 삭제
        String key = jwtTokenProvider.getRefreshTokenKey(authentication);
        if (redisRepository.hasKey(key)) redisRepository.delete(key);

        String accessToken = accessTokenRequest.substring(7);

        // Access 토큰의 유효시간을 가져옴
        Long expiration = jwtTokenProvider.getExpiration(accessToken);

        // 블랙리스트에 저장
        // Redis 에 저장되는 key 값: accessToken / value: LOGOUT_TOKEN / expire: accessToken 의 만료시간
        redisRepository.save(accessToken, BLACK_LIST_VALUE, expiration);

        // SecurityContext 에 있는 authentication 객체를 삭제.
        SecurityContextHolder.clearContext();
    }

    @Override
    public TokenResponse socialRegister(OAuth2RegisterRequest request) {
        PrincipalDetails oAuth2User = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication();

        Member member = request.toEntity(oAuth2User.getMember(), passwordEncoder);

        memberRepository.save(member);

        // 인증 정보를 기반으로 토큰(access, refresh, expiration) 생성
        TokenResponse tokenResponse = jwtTokenProvider.generateToken((Authentication) oAuth2User);

        // RefreshToken Redis 저장, expirationTime 이 지나면 자동 삭제
        redisRepository.save(jwtTokenProvider.getRefreshTokenKey((Authentication) oAuth2User), tokenResponse);

        return tokenResponse;
    }

    @Override
    public MemberProfileResponse getLoggedMemberProfile(String accessTokenRequest) {
        return null;
    }

    /**
     * 유효하지 않은 토큰인지 확인
     *
     * @param token
     * @return
     */
    private boolean isInvalidationToken(String token) {
        return !jwtTokenProvider.validateToken(token);
    }
}
