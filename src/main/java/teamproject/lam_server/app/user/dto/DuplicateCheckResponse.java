package teamproject.lam_server.app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DuplicateCheckResponse {
    private Boolean condition;
    private String value;
}
