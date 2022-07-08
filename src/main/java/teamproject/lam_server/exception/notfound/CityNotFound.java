package teamproject.lam_server.exception.notfound;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.CITY_NOT_FOUND;

public class CityNotFound extends LiveamonthException {

    public CityNotFound() {
        super(CITY_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return CITY_NOT_FOUND;
    }
}
