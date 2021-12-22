package teamproject.lam_server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorConst {
    OperationNotAuthorized(6000,"승인되지 않은 작업"),
    DuplicatedIdFound(6001,"wndqh"),
    UserNotFoundCException(6002,"등록되지 않은 사용자");
    private int code;
    private String description;
}
