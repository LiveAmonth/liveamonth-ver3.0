package teamproject.lam_server.domain;

import lombok.*;
import teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;

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

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "city_id")
    private City city;
}
