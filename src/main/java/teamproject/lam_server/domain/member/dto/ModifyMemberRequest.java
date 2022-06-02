package teamproject.lam_server.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ModifyMemberRequest {

    @Size(max = 20)
    private String nickname;

    private String image;
}
