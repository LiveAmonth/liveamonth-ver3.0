package teamproject.lam_simple.domain;

import lombok.Getter;
import lombok.Setter;
import teamproject.lam_simple.constants.CategoryConstants.TransportCategory;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "city_transports")
@Getter @Setter
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
