package teamproject.lam_server.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static teamproject.lam_server.exception.ErrorCode.*;
import static teamproject.lam_server.global.dto.response.CustomResponse.setResponse;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        if (exception == null) {
            setResponse(response, UNKNOWN_SERVER_ERROR);
        }
        //잘못된 타입의 토큰인 경우
        else if (exception.equals(INVALID_TOKEN.name())) {
            setResponse(response, INVALID_TOKEN);
        }
        //토큰 만료된 경우
        else if (exception.equals(EXPIRED_JWT.name())) {
            setResponse(response, EXPIRED_JWT);
        }
        //지원되지 않는 토큰인 경우
        else if (exception.equals(UNSUPPORTED_TOKEN.name())) {
            setResponse(response, UNSUPPORTED_TOKEN);
        } else {
            setResponse(response, ALREADY_USED_TOKEN);
        }

        if (authException instanceof BadCredentialsException) {
            setResponse(response, BAD_CREDENTIALS);
        }
    }

}
