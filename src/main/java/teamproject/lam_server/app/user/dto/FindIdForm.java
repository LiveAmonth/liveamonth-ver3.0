package teamproject.lam_server.app.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class FindIdForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email_id;

    @NotEmpty
    private String email_domain;

    public String unifyEmail() {
        return this.email_id + "@" + this.email_domain;
    }
}
