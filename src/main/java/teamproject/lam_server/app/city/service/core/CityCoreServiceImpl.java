package teamproject.lam_server.app.city.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.city.repository.core.CityInfoRepository;
import teamproject.lam_server.app.city.repository.core.CityTransportRepository;
import teamproject.lam_server.app.city.repository.query.CityQueryRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityCoreServiceImpl implements CityCoreService{

    private final CityInfoRepository cityInfoRepository;
    private final CityTransportRepository cityTransportRepository;
    private final CityQueryRepository cityQueryRepository;

    //==조회==//

}
