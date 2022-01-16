package teamproject.lam_server.app.city.entity;

import lombok.Getter;
import teamproject.lam_server.constants.CategoryConstants.TransportCategory;

import javax.persistence.*;

@Entity
@DiscriminatorValue("TRANSPORT")
@Getter
public class CityTransport extends City{

    @Enumerated(EnumType.STRING)
    @Column(name = "city_transport_category")
    private TransportCategory category;

    private int station_count;

}
