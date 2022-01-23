package teamproject.lam_server.validator;

import org.junit.jupiter.api.Test;
import teamproject.lam_server.app.user.dto.CreateUserRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.Date;
import java.util.Set;

import static teamproject.lam_server.constants.CategoryConstants.GenderTypes.*;

class joinUserValidatorTest {

    @Test
    void beanValidation_미래날짜선택(){

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setBirth(new Date(2022,02,02));
        createUserRequest.setLoginId("rbdus");
        createUserRequest.setPassword("home8324!");
        createUserRequest.setPasswordCheck("home8324!");
        createUserRequest.setName("이규연");
        createUserRequest.setNickname("hihihi");
        createUserRequest.setGender(FEMALE);
        createUserRequest.setEmailId("rbdss");
        createUserRequest.setEmailDomain("naver.com");

        Set<ConstraintViolation<CreateUserRequest>> violations = validator.validate(createUserRequest);
        for (ConstraintViolation<CreateUserRequest> violation : violations) {
            System.out.println("violation=" + violation);
            System.out.println("violation.message=" + violation.getMessage());
        }

    }

}