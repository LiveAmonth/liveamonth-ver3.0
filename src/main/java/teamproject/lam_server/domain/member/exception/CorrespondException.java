package teamproject.lam_server.domain.member.exception;

import static teamproject.lam_server.domain.member.exception.messages.UserExceptionMessages.CORRESPOND_REFRESH_TOKEN_MESSAGE;

public class CorrespondException extends RuntimeException{

    public CorrespondException() {
        super(CORRESPOND_REFRESH_TOKEN_MESSAGE.getMessage());
    }
}
