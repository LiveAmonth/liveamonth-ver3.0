package teamproject.lam_server.app.city.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static teamproject.lam_server.constants.CategoryConstants.*;

@Data
@AllArgsConstructor
public class CreateCityInfoRequest {

    private CityName name;

    @Enumerated(EnumType.STRING)
    private CityInfoCategory category;

    @Lob
    @NotEmpty
    private String content;

    @NotEmpty
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)",message = "이미지 확장자가 아닙니다.")
    private String image;

    public CityInfo toEntity() {
        return CityInfo.builder()
                .name(name)
                .category(this.category)
                .content(this.content)
                .image(this.image)
                .build();
    }

}
