package teamproject.lam_server.app.city.domain;

import lombok.Getter;
import teamproject.lam_server.constants.CategoryConstants.TransportCategory;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "city_transports")
@Getter
public class CityTransport {

    @Id @GeneratedValue
    @Column(name = "city_transport_id")
    private long id;

    @Enumerated(EnumType.STRING)
    private TransportCategory transport_category;
    private int station_count;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "city_id")
    private City city;

}
