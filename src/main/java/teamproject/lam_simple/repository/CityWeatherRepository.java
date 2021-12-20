package teamproject.lam_simple.repository;

import org.springframework.data.repository.CrudRepository;
import teamproject.lam_simple.constants.CategoryConstants;
import teamproject.lam_simple.constants.CategoryConstants.CityNames;
import teamproject.lam_simple.domain.CityTransport;
import teamproject.lam_simple.domain.CityWeather;

import java.util.List;

public interface CityWeatherRepository extends CrudRepository<CityWeather,Long> {
    List<CityWeather> findCityWeathersByCity_Name(CityNames name);

}
