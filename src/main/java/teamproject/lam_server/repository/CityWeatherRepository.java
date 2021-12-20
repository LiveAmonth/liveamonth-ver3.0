package teamproject.lam_server.repository;

import org.springframework.data.repository.CrudRepository;
import teamproject.lam_server.constants.CategoryConstants.CityNames;
import teamproject.lam_server.domain.CityWeather;

import java.util.List;

public interface CityWeatherRepository extends CrudRepository<CityWeather,Long> {
    List<CityWeather> findCityWeathersByCity_Name(CityNames name);

}
