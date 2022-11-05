package teamproject.lam_server.domain.member.service;

import teamproject.lam_server.domain.member.dto.request.MemberCreate;

public interface MemberAdminService {

    void createManager(MemberCreate create);

    void delete(Long id);

}
