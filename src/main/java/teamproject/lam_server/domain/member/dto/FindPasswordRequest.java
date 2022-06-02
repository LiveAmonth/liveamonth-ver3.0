package teamproject.lam_server.domain.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FindPasswordRequest {

    @NotBlank
    private String loginId;
    @NotBlank
    private String email_id;
    @NotBlank
    private String email_domain;

}
