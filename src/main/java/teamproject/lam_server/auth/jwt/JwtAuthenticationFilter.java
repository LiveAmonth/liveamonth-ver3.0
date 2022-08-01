package teamproject.lam_server.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import teamproject.lam_server.exception.badrequest.AlreadyUsedToken;
import teamproject.lam_server.redis.RedisRepository;
import teamproject.lam_server.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static teamproject.lam_server.exception.ErrorCode.*;
import static teamproject.lam_server.util.JwtUtil.AUTHORIZATION_HEADER;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtProvider;
    private final RedisRepository redisRepository;

    /**
     * 1. Request Header에서 토큰 정보 추출
     * 2. 토큰 정보가 있으면 JwtTokenProvider 의 validToken 를 통해 유효성 검사
     * 3. 토큰이 유효하면 토큰에서 Authentication 객체를 Security Context 에 저장
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = JwtUtil.extractAccessToken(request.getHeader(AUTHORIZATION_HEADER));
        try {
            if (accessToken != null && jwtProvider.validateToken(accessToken)) {
                // 블랙리스트에 있는 토큰인지 확인
                if (this.isInBlackList(accessToken)) throw new AlreadyUsedToken();
                // Authentication 객체를 꺼냄
                Authentication authentication = jwtProvider.getAuthentication(accessToken);
                // Security Context 에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", INVALID_TOKEN.name());
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", EXPIRED_JWT.name());
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", UNSUPPORTED_TOKEN.name());
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", EMPTY_TOKEN.name());
        } catch (Exception e) {
            log.error("================================================");
            log.error("JwtFilter - doFilterInternal() 오류발생");
            log.error("token : {}", accessToken);
            log.error("Exception Message : {}", e.getMessage());
            log.error("Exception StackTrace : {");
            e.printStackTrace();
            log.error("}");
            log.error("================================================");
            request.setAttribute("exception", UNKNOWN_SERVER_ERROR.name());
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Redis 의 블랙 리스트에 Access Token 정보가 있는지 확인
     *
     * @param accessToken
     * @return Boolean
     */
    private Boolean isInBlackList(String accessToken) {
        return redisRepository.hasKey(accessToken);
    }
}
