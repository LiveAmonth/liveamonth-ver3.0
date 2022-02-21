package teamproject.lam_server.app.member.exception;

import static teamproject.lam_server.app.member.exception.messages.UserExceptionMessages.CORRESPOND_REFRESH_TOKEN_MESSAGE;
import static teamproject.lam_server.app.member.exception.messages.UserExceptionMessages.VALID_REFRESH_TOKEN_MESSAGE;

public class CorrespondException extends RuntimeException{

    public CorrespondException() {
        super(CORRESPOND_REFRESH_TOKEN_MESSAGE.getMessage());
    }
}
