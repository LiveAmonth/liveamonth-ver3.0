package teamproject.lam_server.exception.notfound;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.SERVICE_NOT_FOUND;

public class ServiceNotFound extends LiveamonthException {

    public ServiceNotFound() {
        super(SERVICE_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return SERVICE_NOT_FOUND;
    }
}
