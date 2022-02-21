package teamproject.lam_server.app.member.exception;

import static teamproject.lam_server.app.member.exception.messages.UserExceptionMessages.VALID_REFRESH_TOKEN_MESSAGE;

public class ValidTokenException extends RuntimeException{

    public ValidTokenException() {
        super(VALID_REFRESH_TOKEN_MESSAGE.getMessage());
    }

}
