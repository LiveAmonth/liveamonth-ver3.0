package teamproject.lam_server.domain.member.service;

import teamproject.lam_server.domain.city.dto.request.CreateCityIntroRequest;
import teamproject.lam_server.domain.member.dto.SimpleMemberResponse;

public interface MemberService {

    SimpleMemberResponse save(CreateCityIntroRequest request);
}
