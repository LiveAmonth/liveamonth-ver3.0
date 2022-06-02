package teamproject.lam_server.domain.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import teamproject.lam_server.domain.member.exception.messages.UserExceptionMessages;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class NormalUserDeleteException extends RuntimeException {
    public NormalUserDeleteException(Long id) {
        super(UserExceptionMessages.NORMAL_USER_OR_NOT_FOUND_EXCEPTION_MESSAGES.getMessage(id));
    }
}
