package teamproject.lam_server.domain.city.entity;

import lombok.Getter;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;

@MappedSuperclass
@Getter
public abstract class City extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    protected CityName name;
}
