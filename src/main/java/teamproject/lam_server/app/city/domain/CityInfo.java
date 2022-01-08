package teamproject.lam_server.app.city.domain;

import lombok.*;
import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "city_infos")
@Getter
public class CityInfo {

    @Id
    @GeneratedValue
    @Column(name = "city_info_id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "city_info_category")
    private CityInfoCategory cityInfoCategory;

    @Lob
    private String content;

    private String image;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "city_id")
    private City city;
}
