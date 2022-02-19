package teamproject.lam_server.app.member.service;

import teamproject.lam_server.app.city.dto.CreateCityInfoRequest;
import teamproject.lam_server.app.member.dto.SimpleMemberResponse;

public interface MemberService {

    SimpleMemberResponse save(CreateCityInfoRequest request);
}
