package teamproject.lam_server.app.city.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.app.city.entity.CityInfo;

@Repository
public interface CityInfoRepository extends JpaRepository<CityInfo,Long> {

}
