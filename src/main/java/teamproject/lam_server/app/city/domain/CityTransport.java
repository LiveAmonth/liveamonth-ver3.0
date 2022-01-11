package teamproject.lam_server.app.city.domain;

import lombok.Getter;
import teamproject.lam_server.constants.CategoryConstants.TransportCategory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "city_transports")
@Getter
public class CityTransport extends City{


    @Enumerated(EnumType.STRING)
    private TransportCategory transport_category;

    private int station_count;

}
