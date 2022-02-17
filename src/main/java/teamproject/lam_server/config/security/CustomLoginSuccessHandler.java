package teamproject.lam_server.config.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import teamproject.lam_server.app.user.domain.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,final HttpServletResponse response,final Authentication authentication) throws ServletException, IOException {
        final User user = ((MyUserDetails) authentication.getPrincipal()).getUser();

    }
}
