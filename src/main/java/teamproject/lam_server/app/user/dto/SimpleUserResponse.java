package teamproject.lam_server.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleUserResponse {
    private Long id;
    private String userId;

    public SimpleUserResponse(Long id) {
        this.id = id;
    }
    public SimpleUserResponse(String userId) {
        this.userId = userId;
    }
}
