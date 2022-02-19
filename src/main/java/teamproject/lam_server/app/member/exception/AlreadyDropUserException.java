package teamproject.lam_server.app.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static teamproject.lam_server.app.member.exception.messages.UserExceptionMessages.ALREADY_DROP_USER_EXCEPTION_MESSAGE;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class AlreadyDropUserException extends RuntimeException {
    public AlreadyDropUserException(Long id) {
        super(ALREADY_DROP_USER_EXCEPTION_MESSAGE.getMessage(id));

    }
}
