package com.lam.liveamonthapp.auth.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import com.lam.liveamonthapp.auth.dto.response.TokenResponse;
import com.lam.liveamonthapp.auth.jwt.JwtTokenProvider;
import com.lam.liveamonthapp.redis.RedisRepository;
import com.lam.liveamonthapp.util.CookieUtil;
import com.lam.liveamonthapp.util.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.lam.liveamonthapp.domain.member.constants.Role.GUEST;


@RequiredArgsConstructor
@Component
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider tokenProvider;
    private final RedisRepository redisRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (isGuest(authentication)) {
            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8081")
                    .path("/register-user-info")
                    .encode()
                    .build().toUriString();
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        } else {
            TokenResponse tokenResponse = tokenProvider.generateToken(authentication);
            redisRepository.save(tokenProvider.getRefreshTokenKey(authentication), tokenResponse);
            String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:8081")
                    .path("/")
                    .queryParam("access-token", tokenResponse.getAccessToken())
                    .queryParam("token-type", JwtUtil.BEARER_TYPE)
                    .build().toUriString();
            response.addCookie(CookieUtil.addRefreshTokenCookie(tokenResponse.getRefreshToken()));
            getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }

    private boolean isGuest(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> Objects.equals(grantedAuthority.toString(), GUEST.getValue()));
    }
}
