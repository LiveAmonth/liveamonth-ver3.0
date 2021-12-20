package teamproject.lam_server.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FindPwForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String email_id;

    @NotEmpty
    private String email_domain;

    public String unifyEmail() {
        return this.email_id + "@" + this.email_domain;
    }
}
