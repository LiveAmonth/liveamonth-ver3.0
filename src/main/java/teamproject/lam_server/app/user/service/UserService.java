package teamproject.lam_server.app.user.service;

import teamproject.lam_server.app.city.dto.CreateCityInfoRequest;
import teamproject.lam_server.app.user.dto.CreateUserResponse;

public interface UserService {

    CreateUserResponse save(CreateCityInfoRequest request);
}
