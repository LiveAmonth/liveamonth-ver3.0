package teamproject.lam_server.app.member.exception;

import static teamproject.lam_server.app.member.exception.messages.UserExceptionMessages.USER_NOT_FOUND_EXCEPTION_MESSAGES;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super(USER_NOT_FOUND_EXCEPTION_MESSAGES.getMessage(id));
    }
    public UserNotFoundException() {
        super(USER_NOT_FOUND_EXCEPTION_MESSAGES.getMessage());
    }
}
