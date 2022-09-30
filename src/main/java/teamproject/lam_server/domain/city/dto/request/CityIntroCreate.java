package teamproject.lam_server.domain.city.dto.request;

import lombok.*;
import teamproject.lam_server.domain.city.constants.CityIntroCategory;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityIntro;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityIntroCreate {
    @NotNull
    private CityName name;

    @NotNull
    private CityIntroCategory category;

    @Lob
    @NotEmpty
    private String content;

    @NotEmpty
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)", message = "지원하지 않는 확장자입니다.")
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
