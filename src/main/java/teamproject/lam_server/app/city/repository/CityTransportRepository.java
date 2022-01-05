package teamproject.lam_server.app.city.repository;

import org.springframework.data.repository.CrudRepository;
import teamproject.lam_server.constants.CategoryConstants.CityNames;
import teamproject.lam_server.app.city.domain.CityTransport;

import java.util.List;

public interface CityTransportRepository extends CrudRepository<CityTransport,Long> {

    List<CityTransport> findCityTransportsByCity_Name(CityNames name);
}
