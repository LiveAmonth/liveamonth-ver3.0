package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.ALREADY_FOLLOW_MEMBER;
import static teamproject.lam_server.exception.ErrorCode.ALREADY_LIKE_SCHEDULE;

public class AlreadyLikeSchedule extends LiveamonthException {
    public AlreadyLikeSchedule() {
        super(ALREADY_LIKE_SCHEDULE.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return ALREADY_LIKE_SCHEDULE;
    }
}
