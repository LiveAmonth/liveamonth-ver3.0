package teamproject.lam_server.exception.badrequest;

import teamproject.lam_server.exception.ErrorCode;
import teamproject.lam_server.exception.LiveamonthException;

import static teamproject.lam_server.exception.ErrorCode.PERMISSION_NOT_ACCESSIBLE;

public class PermissionNotAccessible extends LiveamonthException {
    public PermissionNotAccessible() {
        super(PERMISSION_NOT_ACCESSIBLE.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return PERMISSION_NOT_ACCESSIBLE;
    }
}
