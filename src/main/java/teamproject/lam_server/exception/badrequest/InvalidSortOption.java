package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.INVALID_SORT_OPTION;

public class InvalidSortOption extends LiveamonthException {
    public InvalidSortOption() {
        super(INVALID_SORT_OPTION.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return INVALID_SORT_OPTION;
    }
}
