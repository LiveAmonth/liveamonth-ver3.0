package teamproject.lam_server.app.city.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;

import javax.persistence.*;

@Entity
@DiscriminatorValue("INFO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityInfo extends City{

    @Enumerated(EnumType.STRING)
    @Column(name = "city_info_category")
    private CityInfoCategory category;

    @Lob
    private String content;

    private String image;

    @Builder
    public CityInfo(CityInfoCategory category, String content, String image) {
        this.category = category;
        this.content = content;
        this.image = image;
    }
}
