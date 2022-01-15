package teamproject.lam_server.app.city.entity;

import lombok.Getter;
import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;

import javax.persistence.*;

@Entity
@Table(name = "city_infos")
@Getter
public class CityInfo extends City{


    @Enumerated(EnumType.STRING)
    @Column(name = "city_info_category")
    private CityInfoCategory cityInfoCategory;

    @Lob
    private String content;

    private String image;

}
