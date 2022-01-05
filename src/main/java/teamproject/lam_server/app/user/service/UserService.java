package teamproject.lam_server.app.user.service;

import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.UserForm;

import java.util.Map;

public interface UserService {

    User login(String loginId, String password);

    User findId(String name, String email);

    Map<String, Object> findPw(String loginId, String email);

    Long save(UserForm userForm);

    void editUserImage(Long id, String image);

    User find(long id);
}

