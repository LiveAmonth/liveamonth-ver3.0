package teamproject.lam_server.app.city.entity;

import lombok.*;
import teamproject.lam_server.app.city.converter.CityInfoCategoryConverter;
import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;
import teamproject.lam_server.constants.CategoryConstants.CityName;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"name", "cityInfoCat", "content", "image"})
public class CityInfo extends City {

    @Convert(converter = CityInfoCategoryConverter.class)
    private CityInfoCategory cityInfoCat;

    @Lob
    private String content;

    private String image;

    @Builder
    public CityInfo(CityName name, CityInfoCategory category, String content, String image) {
        this.name = name;
        this.cityInfoCat = category;
        this.content = content;
        this.image = image;
    }

}
