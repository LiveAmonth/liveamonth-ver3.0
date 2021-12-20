package teamproject.lam_simple.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.util.Assert;
import teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory;

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

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "city_id")
    private City city;
}
