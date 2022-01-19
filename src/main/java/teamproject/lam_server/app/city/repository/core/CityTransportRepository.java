package teamproject.lam_server.app.city.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.app.city.entity.City;
import teamproject.lam_server.app.city.entity.CityTransport;

import java.util.List;

import static teamproject.lam_server.constants.CategoryConstants.CityName;

@Repository
public interface CityTransportRepository extends JpaRepository<CityTransport, Long> {

    List<CityTransport> findByName(CityName name);

}
