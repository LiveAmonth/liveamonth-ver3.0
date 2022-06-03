package teamproject.lam_server.domain.member.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class FindIdRequest {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z가-힣]{2,20}")
    private String name;

    @NotBlank
    @Pattern(regexp = "[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*")
    private String email;
}
