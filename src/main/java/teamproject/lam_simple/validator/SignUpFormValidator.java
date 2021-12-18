package teamproject.lam_simple.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import teamproject.lam_simple.dto.UserForm;
import teamproject.lam_simple.repository.UserRepository;

import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class SignUpFormValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserForm userForm = (UserForm) target;
        if (userForm.getBirth().toLocalDate().isAfter(new java.sql.Date(System.currentTimeMillis()).toLocalDate())) {
            errors.reject("past.birth");
        }
        if (!userForm.getPassword().equals(userForm.getPasswordCheck())) {
            errors.reject("passwordCheck");
        }
        if (userRepository.existsByEmail(userForm.unifyEmail())) {
            errors.reject("exists.email");
        }
        if (userRepository.existsByNickname(userForm.getNickname())) {
            errors.reject("exists.nickname");
        }
        if (userRepository.existsByLoginId(userForm.getLoginId())) {
            errors.reject("exists.loginId");
        }
    }
}
