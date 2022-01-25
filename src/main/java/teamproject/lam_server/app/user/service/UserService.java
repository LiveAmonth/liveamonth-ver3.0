package teamproject.lam_server.app.user.service;

import teamproject.lam_server.app.city.dto.CreateCityInfoRequest;
import teamproject.lam_server.app.user.dto.SimpleUserResponse;

public interface UserService {

    SimpleUserResponse save(CreateCityInfoRequest request);
}
