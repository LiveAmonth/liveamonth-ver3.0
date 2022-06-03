package teamproject.lam_server.domain.member.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class FindPasswordRequest {

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9]{3,20}")
    private String loginId;

    @NotNull
    @Pattern(regexp = "[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*")
    private String email;
}
