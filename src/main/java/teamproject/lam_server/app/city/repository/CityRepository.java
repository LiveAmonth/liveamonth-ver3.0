package teamproject.lam_server.app.city.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.app.city.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {


}
