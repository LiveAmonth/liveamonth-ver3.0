package teamproject.lam_server.exception.notfound;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.REVIEW_NOT_FOUND;

public class ReviewNotFound extends LiveamonthException {

    public ReviewNotFound() {
        super(REVIEW_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return REVIEW_NOT_FOUND;
    }
}
