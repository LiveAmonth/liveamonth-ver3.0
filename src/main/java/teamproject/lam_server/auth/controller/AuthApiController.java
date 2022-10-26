package teamproject.lam_server.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.auth.dto.response.AccessTokenResponse;
import teamproject.lam_server.auth.dto.response.TokenResponse;
import teamproject.lam_server.auth.service.AuthService;
import teamproject.lam_server.domain.member.dto.request.MemberLogin;
import teamproject.lam_server.global.dto.response.CustomResponse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.*;
import static teamproject.lam_server.util.CookieUtil.addRefreshTokenCookie;
import static teamproject.lam_server.util.CookieUtil.deleteRefreshTokenCookie;
import static teamproject.lam_server.util.JwtUtil.extractAccessToken;


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
