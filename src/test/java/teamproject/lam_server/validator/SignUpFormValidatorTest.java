package teamproject.lam_server.validator;

import org.junit.jupiter.api.Test;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.app.user.dto.UserForm;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Date;
import java.util.Set;

import static teamproject.lam_server.constants.CategoryConstants.GenderTypes.*;

class SignUpFormValidatorTest {

    @Test
    void beanValidation_미래날짜선택(){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        UserForm userForm = new UserForm();
        userForm.setBirth(new Date(2022,02,02));
        userForm.setLoginId("rbdus");
        userForm.setPassword("home8324!");
        userForm.setPasswordCheck("home8324!");
        userForm.setName("이규연");
        userForm.setNickname("hihihi");
        userForm.setGenderTypes(FEMALE);
        userForm.setEmail_id("rbdss");
        userForm.setEmail_domain("naver.com");

        Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm);
        for (ConstraintViolation<UserForm> violation : violations) {
            System.out.println("violation=" + violation);
            System.out.println("violation.message=" + violation.getMessage());
        }

    }

}