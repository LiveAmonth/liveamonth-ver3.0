package teamproject.lam_server.domain.city.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityTransport;

import java.util.List;

@Repository
public interface CityTransportRepository extends JpaRepository<CityTransport, Long> {

    List<CityTransport> findByName(CityName name);

}
