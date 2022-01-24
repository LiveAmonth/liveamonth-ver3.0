package teamproject.lam_server.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserResponse {
    private Long id;
    private String userId;
}
