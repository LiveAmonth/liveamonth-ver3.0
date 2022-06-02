package teamproject.lam_server.domain.city.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityWeather;

import java.util.List;

@Repository
public interface CityWeatherRepository extends JpaRepository<CityWeather, Long> {

    List<CityWeather> findByName(CityName name);

}
