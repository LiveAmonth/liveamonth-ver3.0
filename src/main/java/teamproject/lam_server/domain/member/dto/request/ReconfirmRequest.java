package teamproject.lam_server.domain.member.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReconfirmRequest {

    @NotBlank
    private String password;
}
