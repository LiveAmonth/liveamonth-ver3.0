package teamproject.lam_server.domain.city.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.city.constants.CityInfoCategory;
import teamproject.lam_server.domain.city.constants.CityName;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CityInfo extends City {

    @Enumerated(EnumType.STRING)
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
