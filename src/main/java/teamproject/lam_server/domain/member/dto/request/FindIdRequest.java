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
    @Pattern(regexp = "[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
    private String email;
}
