package teamproject.lam_server.app.city.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.constants.CategoryConstants.CityNames;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter @NoArgsConstructor
public abstract class City {

    @Id @GeneratedValue
    @Column(name = "city_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private CityNames name;

}
