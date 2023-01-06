package com.lam.liveamonthapp.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lam.liveamonthapp.auth.dto.response.AccessTokenResponse;
import com.lam.liveamonthapp.auth.dto.response.TokenResponse;
import com.lam.liveamonthapp.auth.service.AuthService;
import com.lam.liveamonthapp.domain.member.dto.request.MemberLogin;
import com.lam.liveamonthapp.global.dto.response.CustomResponse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.lam.liveamonthapp.global.constants.ResponseMessage.*;
import static com.lam.liveamonthapp.util.CookieUtil.addRefreshTokenCookie;
import static com.lam.liveamonthapp.util.CookieUtil.deleteRefreshTokenCookie;
import static com.lam.liveamonthapp.util.JwtUtil.extractAccessToken;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody MemberLogin request, HttpServletResponse response) {
        TokenResponse result = authService.login(request);
        response.addCookie(addRefreshTokenCookie(result.getRefreshToken()));
        return CustomResponse.success(LOGIN_SUCCESS, AccessTokenResponse.of(result.getAccessToken()));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(
            @RequestHeader(value = "Authorization") String bearerToken,
            @CookieValue(value = "Refresh-Token", required = false, defaultValue = "expired") String refreshToken) {
        TokenResponse result = authService.reissue(extractAccessToken(bearerToken), refreshToken);
//        response.addCookie(addRefreshTokenCookie(result.getRefreshToken()));
        return CustomResponse.success(REISSUE_TOKEN_SUCCESS, AccessTokenResponse.of(result.getAccessToken()));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization") String bearerToken,
            HttpServletResponse response) {
        authService.logout(extractAccessToken(bearerToken));
        response.addCookie(deleteRefreshTokenCookie());
        return CustomResponse.success(LOGOUT_SUCCESS);
    }

//    @PostMapping("/login/register")
//    public ResponseEntity<?> registerBasicProfile(@Valid @RequestBody OAuth2RegisterEdit request, HttpServletResponse response) {
//        TokenResponse result = authService.socialRegister(request);
//        return CustomResponse.success(LOGIN_SUCCESS, AccessTokenResponse.of(result.getAccessToken()));
//    }
}
