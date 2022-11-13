package teamproject.lam_server.domain.city.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.city.constants.CityName;
import teamproject.lam_server.domain.city.entity.CityIntro;

import java.util.List;

public interface CityIntroRepository extends JpaRepository<CityIntro, Long> {

    List<CityIntro> findByName(CityName cityName);

    @Transactional
    @Modifying
    @Query("delete from CityIntro c where c.id in :ids")
    void deleteAllByIdInQuery(@Param("ids") List<Long> ids);
}
