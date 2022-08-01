package teamproject.lam_server.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import teamproject.lam_server.auth.dto.AccessTokenResponse;
import teamproject.lam_server.auth.dto.TokenResponse;
import teamproject.lam_server.auth.service.AuthService;
import teamproject.lam_server.domain.member.dto.request.LoginRequest;
import teamproject.lam_server.domain.member.dto.request.OAuth2RegisterRequest;
import teamproject.lam_server.global.dto.CustomResponse;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static teamproject.lam_server.global.constants.ResponseMessage.*;
import static teamproject.lam_server.util.CookieUtil.addRefreshTokenCookie;
import static teamproject.lam_server.util.CookieUtil.deleteRefreshTokenCookie;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthApiController {

    private final AuthService authService;

    @GetMapping("/logged/profile")
    public ResponseEntity<?> getLoggedMemberProfile(@RequestHeader(value = "Authorization") String accessTokenRequest){
        authService.getLoggedMemberProfile(accessTokenRequest);
        return null;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        TokenResponse result = authService.login(request);
        response.addCookie(addRefreshTokenCookie(result.getRefreshToken()));
        return CustomResponse.success(LOGIN_SUCCESS, AccessTokenResponse.of(result.getAccessToken()));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(
            @CookieValue(value = "Refresh-Token") String refreshTokenRequest, HttpServletResponse response) {
        TokenResponse result = authService.reissue(refreshTokenRequest);
        response.addCookie(addRefreshTokenCookie(result.getRefreshToken()));
        return CustomResponse.success(REISSUE_TOKEN_SUCCESS, AccessTokenResponse.of(result.getAccessToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization") String accessTokenRequest,
            HttpServletResponse response) {
        authService.logout(accessTokenRequest);
        response.addCookie(deleteRefreshTokenCookie());
        return CustomResponse.success(LOGOUT_SUCCESS);
    }

    @PostMapping("/login/register")
    public ResponseEntity<?> registerBasicProfile(@Valid @RequestBody OAuth2RegisterRequest request, HttpServletResponse response) {
        TokenResponse result = authService.socialRegister(request);
        return CustomResponse.success(LOGIN_SUCCESS, AccessTokenResponse.of(result.getAccessToken()));
    }
}
