package teamproject.lam_simple.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import teamproject.lam_simple.constants.SessionConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static teamproject.lam_simple.constants.PathConstants.NO_CERTIFIED_REDIRECT;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession();

        if (session == null || session.getAttribute(SessionConstants.LOGIN_USER) == null) {
            // 로그인으로 redirect
            response.sendRedirect(NO_CERTIFIED_REDIRECT + requestURI);
            return false;
        }
        return true;
    }
}
