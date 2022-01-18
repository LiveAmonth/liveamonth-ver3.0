package teamproject.lam_server.app.city.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.app.city.entity.CityInfo;
import teamproject.lam_server.constants.CategoryConstants.CityName;

import java.util.List;
import java.util.Optional;

import static teamproject.lam_server.constants.CategoryConstants.CityInfoCategory;

@Repository
public interface CityInfoRepository extends JpaRepository<CityInfo, Long> {

    List<CityInfo> findByCityInfoCatNot(CityInfoCategory cityInfoCat);

    Optional<CityInfo> findByNameAndCityInfoCat(CityName name, CityInfoCategory cityInfoCat);

}
