package teamproject.lam_server.util;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.util.StringUtils.hasText;

public abstract class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    public static final String BEARER_TYPE = "Bearer ";

    /**
     * Request Header에서 토큰 정보 추출
     * <p>
     * ex) 'Bearer AS1DM2LAS47DLA9MSD2OMAWd' 와 같은 토큰이 request의 Authorization부분에 들어있음
     * 그러면 앞에서 부터 7번째(Bearer+공백)를 잘라내면 토큰 정보(뒷 부분)를 가져올 수 있다.
     *
     * @return
     */
    public static String extractAccessToken(String bearerToken) {
        return hasBearerToken(bearerToken) ? bearerToken.substring(7) : null;
    }

    public static String extractRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER);
        return hasText(refreshToken) ? refreshToken : null;
    }

    /**
     * null 체크, 공백 체크, 토큰의 앞 부분이 'Bearer' 로 시작하는지 체크
     *
     * @param bearerToken
     * @return
     */
    private static boolean hasBearerToken(String bearerToken) {
        return hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE);
    }

    public static String createRandomPassword() {
        int index = 0;
        char[] charArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z'};

        StringBuffer tempPassword = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            index = (int) (charArr.length * Math.random());
            tempPassword.append(charArr[index]);
        }
        return tempPassword.toString();
    }
}
