package com.lam.liveamonthapp.domain.member.service;

import com.lam.liveamonthapp.domain.member.dto.request.MemberCreate;
import com.lam.liveamonthapp.global.dto.response.PostIdResponse;

public interface MemberAdminService {

    PostIdResponse createManager(MemberCreate create);

    void delete(Long id);

}
