package teamproject.lam_simple.domain;

import lombok.*;
import org.springframework.util.Assert;
import teamproject.lam_simple.constants.CategoryConstants.CityInfoCategory;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@NamedEntityGraph(
        name = "cityInfo-with-city",
        attributeNodes = {
                @NamedAttributeNode("city")
        }
)
@Entity
@Table(name = "city_infos")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public CityInfo(CityInfoCategory cityInfoCategory,String content,String image,City city) {
        Assert.notNull(cityInfoCategory, "cityCategory must not be null");
        Assert.notNull(content, "content must not be empty");
        Assert.notNull(image, "image must not be empty");
        Assert.notNull(city, "city must not be null");
        this.cityInfoCategory = cityInfoCategory;
        this.content = content;
        this.image = image;
        this.city = city;
    }
}
