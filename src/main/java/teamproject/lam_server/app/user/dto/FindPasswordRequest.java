package teamproject.lam_server.app.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class FindPasswordRequest {

    @NotBlank
    private String loginId;
    @NotBlank
    private String email_id;
    @NotBlank
    private String email_domain;

}
