package teamproject.lam_server.app.city.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.app.city.entity.CityWeather;

import java.util.List;

import static teamproject.lam_server.constants.CategoryConstants.CityName;

@Repository
public interface CityWeatherRepository extends JpaRepository<CityWeather, Long> {

    List<CityWeather> findByName(CityName name);

}
