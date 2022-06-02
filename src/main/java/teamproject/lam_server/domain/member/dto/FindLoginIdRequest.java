package teamproject.lam_server.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindLoginIdRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email_id;

    @NotBlank
    private String email_domain;

}
