package teamproject.lam_server.app.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class FindLoginIdRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email_id;

    @NotBlank
    private String email_domain;

}
