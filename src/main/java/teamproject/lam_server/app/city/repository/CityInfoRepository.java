package teamproject.lam_server.app.city.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.constants.CategoryConstants.CityNames;
import teamproject.lam_server.app.city.entity.CityInfo;

import java.util.List;

import static teamproject.lam_server.constants.CategoryConstants.*;

@Repository
public interface CityInfoRepository extends CrudRepository<CityInfo, Long> {
    List<CityInfo> findCityInfosByCityInfoCategory(CityInfoCategory category);

    List<CityInfo> findCityInfosByCityInfoCategoryAndCity_Name(CityInfoCategory category, CityNames name);
}
