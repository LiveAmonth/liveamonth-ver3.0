package teamproject.lam_server.app.user.dto;

import lombok.Data;
import teamproject.lam_server.app.user.domain.User;

@Data
public class FindPasswordResponse {
    private String name;
    private String email;
    private String password;

    public FindPasswordResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
