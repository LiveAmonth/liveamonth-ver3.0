package teamproject.lam_server.dto.user;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;
import teamproject.lam_server.domain.User;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LoginResponse {
    private final Long userId;
    private final List<String> roles;
    private final LocalDateTime createdDate;

    public LoginResponse(User user) {
        this.userId = user.getId();
        this.roles = user.get
    }
}
