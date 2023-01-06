package com.lam.liveamonthapp.domain.city.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.lam.liveamonthapp.domain.city.constants.CityIntroCategory;
import com.lam.liveamonthapp.domain.city.constants.CityName;

import javax.persistence.*;

import static com.lam.liveamonthapp.constants.AttrConstants.IMAGEBB_URL;

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
    public void updateContent(String content) {
        this.content = content;
    }

    //== Getter ==//
    public String getImage() {
        return IMAGEBB_URL + image;
    }
}
