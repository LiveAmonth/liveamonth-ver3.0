package teamproject.lam_server.domain.city.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.domain.city.constants.CityIntroCategory;
import teamproject.lam_server.domain.city.constants.CityName;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "city_intro_id"))
public class CityIntro extends City {

    @Enumerated(EnumType.STRING)
    private CityIntroCategory cityInfoCat;

    @Lob
    private String content;

    private String image;

    @Builder
    public CityIntro(CityName name, CityIntroCategory category, String content, String image) {
        this.name = name;
        this.cityInfoCat = category;
        this.content = content;
        this.image = image;
    }

    //==업데이트 로직==//
    public void updateContent(String content){
        this.content = content;
    }
}
