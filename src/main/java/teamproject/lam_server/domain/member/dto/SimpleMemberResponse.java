package teamproject.lam_server.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleMemberResponse {
    private Long id;
    private String userId;

    public SimpleMemberResponse(Long id) {
        this.id = id;
    }
    public SimpleMemberResponse(String userId) {
        this.userId = userId;
    }
}
