package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.ILLEGAL_OWNER_OF_POST;

public class IllegalWriterOfPost extends LiveamonthException {
    public IllegalWriterOfPost() {
        super(ILLEGAL_OWNER_OF_POST.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ILLEGAL_OWNER_OF_POST;
    }
}
