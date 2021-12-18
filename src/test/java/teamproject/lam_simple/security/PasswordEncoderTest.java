package teamproject.lam_simple.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.lam_simple.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PasswordEncoderTest {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncode(){
        //given
        String rawPW = "12345";

        //when
        String encodePW = passwordEncoder.encode(rawPW);

        //then
        assertAll(
                () -> assertNotEquals(rawPW, encodePW),
                () -> assertTrue(passwordEncoder.matches(rawPW, encodePW))
        );
    }
}
