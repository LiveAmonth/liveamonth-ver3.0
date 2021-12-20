package teamproject.lam_simple.repository;

import org.springframework.data.repository.CrudRepository;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.constants.CategoryConstants.CityNames;
import teamproject.lam_simple.domain.CityTransport;

import java.util.List;

public interface CityTransportRepository extends CrudRepository<CityTransport,Long> {

    List<CityTransport> findCityTransportsByCity_Name(CityNames name);
}
