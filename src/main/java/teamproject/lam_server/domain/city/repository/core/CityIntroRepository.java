package teamproject.lam_server.domain.city.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.city.constants.CityIntroCategory;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityIntro;

import java.util.List;
import java.util.Optional;

public interface CityIntroRepository extends JpaRepository<CityIntro, Long> {

    Optional<CityIntro> findOneByNameAndCityInfoCat(CityName name, CityIntroCategory cityInfoCat);

    List<CityIntro> findByNameAndCityInfoCat(CityName name, CityIntroCategory cityInfoCat);

    @Transactional
    @Modifying
    @Query("delete from CityIntro c where c.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
