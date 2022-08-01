package teamproject.lam_server.util;

import javax.servlet.http.Cookie;

import static teamproject.lam_server.util.JwtUtil.REFRESH_TOKEN_HEADER;

public abstract class CookieUtil {

    public static Cookie addRefreshTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie(REFRESH_TOKEN_HEADER, refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        return cookie;
    }

    public static Cookie deleteRefreshTokenCookie() {
        Cookie cookie = new Cookie(REFRESH_TOKEN_HEADER, "");
        cookie.setMaxAge(0);
        return cookie;
    }
}
