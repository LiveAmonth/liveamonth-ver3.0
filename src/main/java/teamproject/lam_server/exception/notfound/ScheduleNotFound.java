package teamproject.lam_server.exception.notfound;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.SCHEDULE_NOT_FOUND;

public class ScheduleNotFound extends LiveamonthException {

    public ScheduleNotFound() {
        super(SCHEDULE_NOT_FOUND.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return SCHEDULE_NOT_FOUND;
    }
}
