package teamproject.lam_server.app.user.exception;

import static teamproject.lam_server.app.user.exception.messages.UserExceptionMessages.EXISTS_EXCEPTION_MESSAGES;

public class ExistsException extends RuntimeException {
    public ExistsException(String param) {
        super(EXISTS_EXCEPTION_MESSAGES.getExistMessage(param));
    }
}
