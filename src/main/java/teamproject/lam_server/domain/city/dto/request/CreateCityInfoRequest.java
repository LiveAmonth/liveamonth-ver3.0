package teamproject.lam_server.domain.city.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.city.constants.CityInfoCategory;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityInfo;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityInfoRequest {

    private CityName name;

    private CityInfoCategory category;

    @Lob
    @NotEmpty
    private String content;

    @NotEmpty
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)", message = "이미지 확장자가 아닙니다.")
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
