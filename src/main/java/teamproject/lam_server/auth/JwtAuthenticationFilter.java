package teamproject.lam_server.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // JWT token 추출
        String token = resolveToken((HttpServletRequest) request);

        // 유효성 감사 By JWT token
        if(token != null && jwtTokenProvider.validateToken(token)){
            // 블랙 리스트 확인
            if(this.isLogout(token)){
                // SecurityContext 에 토큰 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request,response);
    }

    /**
     * Redis에 저장된 BlackList에 Access token 정보가 있는 지 확인
     * @param token
     * @return Boolean
     */
    private Boolean isLogout(String token){
        return ObjectUtils.isEmpty(redisTemplate.opsForValue().get(token));
    }

    /**
     * Http Header에서 token 정보를 추출
     * @param request
     * @return String
     */
    private String resolveToken(HttpServletRequest request) {
        String bearToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearToken) && bearToken.startsWith(BEARER_TYPE)) {
            return bearToken.substring(7);
        }
        return null;
    }
}
