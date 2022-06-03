package teamproject.lam_server.domain.city.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.city.constants.CityIntroCategory;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityIntro;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityIntroRequest {
    @NotNull
    private CityName name;

    @NotNull
    private CityIntroCategory category;

    @Lob
    @NotEmpty
    private String content;

    @NotEmpty
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)", message = "이미지 확장자가 아닙니다.")
    private String image;

    public CityIntro toEntity() {
        return CityIntro.builder()
                .name(name)
                .category(this.category)
                .content(this.content)
                .image(this.image)
                .build();
    }

}
