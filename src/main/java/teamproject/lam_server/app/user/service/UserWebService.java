package teamproject.lam_server.app.user.service;

import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.CreateUserRequest;

import java.util.Map;

public interface UserWebService {

    User login(String loginId, String password);

    User findId(String name, String email);

    Map<String, Object> findPw(String loginId, String email);

    Long save(CreateUserRequest createUserRequest);

    void editUserImage(Long id, String image);

    User find(long id);
}

