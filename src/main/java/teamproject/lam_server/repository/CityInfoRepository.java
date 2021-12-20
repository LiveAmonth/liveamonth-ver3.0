package teamproject.lam_server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import teamproject.lam_server.constants.CategoryConstants.CityNames;
import teamproject.lam_server.domain.CityInfo;

import java.util.List;

import static teamproject.lam_server.constants.CategoryConstants.*;

@Repository
public interface CityInfoRepository extends CrudRepository<CityInfo,Long> {
    List<CityInfo> findCityInfosByCityInfoCategory(CityInfoCategory category);

    List<CityInfo> findCityInfosByCityInfoCategoryAndCity_Name(CityInfoCategory category, CityNames name);

//    @PersistenceContext
//    private EntityManager em;
//
//    public List<CityInfo> findCityInfoByCategory(CityInfoCategory category){
//        return em.createQuery("select c from CityInfo c where c.cityInfoCategory = :category", CityInfo.class)
//                .setParameter("category", category)
//                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("cityInfo-with-city"))
//                .getResultList();
//    }
//
//    public City findWithGraphByName(CityNames name, String graphName) {
//        return em.createQuery("select c from City c where c.name =: name", City.class)
//                .setParameter("name", name)
////                .setHint("javax.persistence.fetchgraph", em.getEntityGraph(graphName))
//                .getSingleResult();
//    }
//    public City findAllCityInfoWithGraphByName(CityNames name) {
//        return em.createQuery("select c from City c where c.name =: name", City.class)
//                .setParameter("name", name)
////                .setHint("javax.persistence.fetchgraph", "city-with-cityInfos")
////                .setHint("javax.persistence.fetchgraph", "city-with-cityWeathers")
////                .setHint("javax.persistence.fetchgraph", "city-with-cityTransports")
//                .getSingleResult();
//    }
//
//    public List<City> findAll() {
//        return em.createQuery("select c from City c", City.class)
//                .getResultList();
//    }
}
