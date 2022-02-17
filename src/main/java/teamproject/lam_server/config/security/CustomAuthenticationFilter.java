package teamproject.lam_server.config.security;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import teamproject.lam_server.app.user.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken authRequest;
        try{
            final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            authRequest = new UsernamePasswordAuthenticationToken(user.getLoginId(), user.getPassword());
        } catch (IOException e) {
            throw new NoSuchElementException();
        }
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);

    }
}
