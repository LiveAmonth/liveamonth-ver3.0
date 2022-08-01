package teamproject.lam_server.auth.exception;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.global.dto.CustomResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException instanceof BadCredentialsException) {
            setResponse(response,ErrorCode.BAD_CREDENTIALS);
        }else{
            setResponse(response, ErrorCode.ARGUMENTS_NOT_VALID);
        }

    }

    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(errorCode.getStatusCode());

        response.getWriter().print(CustomResponse.fail(errorCode));
    }
}
