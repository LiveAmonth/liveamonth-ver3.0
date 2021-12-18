package teamproject.lam_simple.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class FindIdForm {

    @NotEmpty
    private String name;

    @NotEmpty
    private String email_id;

    @NotEmpty
    private String email_domain;

    public String unifyEmail(){
        return this.email_id+"@"+this.email_domain;
    }
}
