package teamproject.lam_server.app.user.exception.messages;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserExceptionMessages {
    USER_NOT_FOUND_EXCEPTION_MESSAGES(" user not found"),
    ALREADY_DROP_USER_EXCEPTION_MESSAGE(" user already dropped"),
    NORMAL_USER_OR_NOT_FOUND_EXCEPTION_MESSAGES(" user not dropped yet or not found");
    private String message;

    public String getMessage(Long id) {
        return "[ID]" + id + message;
    }

    public String getMessage(String userId) {
        return "[USER ID]" + userId + message;
    }
}
