package teamproject.lam_server.util;

import teamproject.lam_server.global.exception.CustomException;
import teamproject.lam_server.global.exception.ErrorCode;

import java.util.function.Supplier;

public abstract class BasicServiceUtil {
    public static Supplier<CustomException> getExceptionSupplier(ErrorCode errorCode) {
        return () -> new CustomException(errorCode);
    }
}
