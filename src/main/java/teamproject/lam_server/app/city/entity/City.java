package teamproject.lam_server.app.city.entity;

import lombok.Getter;
import teamproject.lam_server.app.city.converter.CityNameConverter;
import teamproject.lam_server.constants.CategoryConstants.CityName;
import teamproject.lam_server.global.entity.BaseTimeEntity;

import javax.persistence.*;

@MappedSuperclass
@Getter
public abstract class City extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @Convert(converter = CityNameConverter.class)
    protected CityName name;
}
