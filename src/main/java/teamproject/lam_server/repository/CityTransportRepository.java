package teamproject.lam_server.repository;

import org.springframework.data.repository.CrudRepository;
import teamproject.lam_server.constants.CategoryConstants.CityNames;
import teamproject.lam_server.domain.CityTransport;

import java.util.List;

public interface CityTransportRepository extends CrudRepository<CityTransport,Long> {

    List<CityTransport> findCityTransportsByCity_Name(CityNames name);
}
