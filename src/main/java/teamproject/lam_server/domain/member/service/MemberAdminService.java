package teamproject.lam_server.domain.member.service;

import teamproject.lam_server.domain.member.dto.request.MemberCreate;
import teamproject.lam_server.global.dto.response.PostIdResponse;

public interface MemberAdminService {

    PostIdResponse createManager(MemberCreate create);

    void delete(Long id);

}
