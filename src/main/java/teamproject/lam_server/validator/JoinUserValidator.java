package teamproject.lam_server.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import teamproject.lam_server.app.user.dto.CreateUserRequest;
import teamproject.lam_server.app.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class JoinUserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(CreateUserRequest.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateUserRequest request = (CreateUserRequest) target;
        if (request.getBirth().isAfter(LocalDate.now())) {
            errors.reject("past.birth");
        }
        if (!request.getPassword().equals(request.getPasswordCheck())) {
            errors.reject("passwordCheck");
        }
        if (userRepository.existsByEmail(unifyEmail(request.getEmailId(), request.getEmailDomain()))) {
            errors.reject("exists.email");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            errors.reject("exists.nickname");
        }
        if (userRepository.existsByLoginId(request.getLoginId())) {
            errors.reject("exists.loginId");
        }
    }
    private String unifyEmail(String id, String domain){
        return id + "@" + domain;
    }
}
