package teamproject.lam_server.exception.notfound;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.INQUIRY_NOT_FOUND;

public class InquiryNotFound extends LiveamonthException {

    public InquiryNotFound() {
        super(INQUIRY_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return INQUIRY_NOT_FOUND;
    }
}
