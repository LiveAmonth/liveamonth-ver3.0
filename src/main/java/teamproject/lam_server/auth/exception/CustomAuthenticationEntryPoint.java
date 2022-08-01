package teamproject.lam_server.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import teamproject.lam_server.exception.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static teamproject.lam_server.global.dto.CustomResponse.setResponse;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof BadCredentialsException) {
            setResponse(response,ErrorCode.BAD_CREDENTIALS);
        }else{
            setResponse(response, ErrorCode.ARGUMENTS_NOT_VALID);
        }

    }

}
