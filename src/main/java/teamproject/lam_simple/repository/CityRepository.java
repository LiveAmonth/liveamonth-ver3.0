package teamproject.lam_simple.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_simple.constants.CategoryConstants.CityNames;
import teamproject.lam_simple.constants.CategoryConstants.Month;
import teamproject.lam_simple.domain.City;
import teamproject.lam_simple.domain.CityInfo;
import teamproject.lam_simple.domain.CityTransport;
import teamproject.lam_simple.domain.CityWeather;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static teamproject.lam_simple.constants.CategoryConstants.*;

@Repository
public class CityRepository {

    @PersistenceContext
    private EntityManager em;

    public List<CityInfo> findCityInfoByCategory(CityInfoCategory category){
        return em.createQuery("select c from CityInfo c where c.cityInfoCategory = :category", CityInfo.class)
                .setParameter("category", category)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("cityInfo-with-city"))
                .getResultList();
    }

    public City findWithGraphByName(CityNames name, String graphName) {
        return em.createQuery("select c from City c where c.name =: name", City.class)
                .setParameter("name", name)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph(graphName))
                .getSingleResult();
    }
    public City findAllCityInfoWithGraphByName(CityNames name) {
        return em.createQuery("select c from City c where c.name =: name", City.class)
                .setParameter("name", name)
                .setHint("javax.persistence.fetchgraph", "city-with-cityInfos")
                .setHint("javax.persistence.fetchgraph", "city-with-cityWeathers")
                .setHint("javax.persistence.fetchgraph", "city-with-cityTransports")
                .getSingleResult();
    }

    public List<City> findAll() {
        return em.createQuery("select c from City c", City.class)
                .getResultList();
    }
}
