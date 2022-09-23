package teamproject.lam_server.domain.member.dto.editor;


import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class PasswordEditor {

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,20}")
    private final String password;

    @Builder
    public PasswordEditor(String password) {
        this.password = password;
    }
}
