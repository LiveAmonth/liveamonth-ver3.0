package teamproject.lam_server.domain.city.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import teamproject.lam_server.domain.city.constants.CityInfoCategory;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityInfo;

import java.util.List;
import java.util.Optional;

public interface CityInfoRepository extends JpaRepository<CityInfo, Long> {

    List<CityInfo> findByCityInfoCat(CityInfoCategory cityInfoCat);

    Optional<CityInfo> findOneByNameAndCityInfoCat(CityName name, CityInfoCategory cityInfoCat);

    List<CityInfo> findByNameAndCityInfoCat(CityName name, CityInfoCategory cityInfoCat);

}
