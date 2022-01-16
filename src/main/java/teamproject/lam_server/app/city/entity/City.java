package teamproject.lam_server.app.city.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import teamproject.lam_server.constants.CategoryConstants;
import teamproject.lam_server.global.entity.BaseEntity;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "cities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class City extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryConstants.CityNames name;
}
