package teamproject.lam_server.app.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static teamproject.lam_server.app.user.exception.messages.UserExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGES;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(USER_NOT_FOUND_EXCEPTION_MESSAGES.getMessage(id));
    }
    public UserNotFoundException() {
        super(USER_NOT_FOUND_EXCEPTION_MESSAGES.getMessage());
    }
}
