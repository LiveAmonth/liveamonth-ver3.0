package teamproject.lam_server.domain.member.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.lam_server.domain.member.constants.GenderType;
import teamproject.lam_server.domain.member.constants.Role;
import teamproject.lam_server.domain.member.entity.Member;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreate {

    @NotBlank
    @Pattern(regexp = "[a-zA-Z\\d]{5,20}")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,20}")
    private String password;

    @NotBlank
    @Pattern(regexp = "[a-zA-Z가-힣]{2,20}")
    private String name;

    @NotBlank
    @Size(max = 20)
    private String nickname;

    @NotBlank
    @Pattern(regexp = "[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}")
    private String email;

    @NotNull
    @Past
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birth;

    @NotNull
    private String gender;

    @Builder
    public MemberCreate(String loginId, String password, String name, String nickname, String email, LocalDate birth, String gender) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.birth = birth;
        this.gender = gender;
    }

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.basicBuilder()
                .loginId(this.loginId)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .nickname(this.nickname)
                .birth(this.birth)
                .email(this.email)
                .gender(GenderType.valueOf(this.gender))
                .role(Role.USER)
                .build();
    }

    public Member toManagerEntity(PasswordEncoder passwordEncoder) {
        return Member.basicBuilder()
                .loginId(this.loginId)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .nickname(this.nickname)
                .birth(this.birth)
                .email(this.email)
                .gender(GenderType.valueOf(this.gender))
                .role(Role.MANAGER)
                .build();
    }

}
