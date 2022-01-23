package teamproject.lam_server.app.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.city.dto.CreateCityInfoRequest;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.CreateUserRequest;
import teamproject.lam_server.app.user.dto.CreateUserResponse;
import teamproject.lam_server.app.user.repository.UserRepository;
import teamproject.lam_server.validator.JoinUserValidator;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl{

    private final UserRepository userRepository;
    private final JoinUserValidator joinUserValidator;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User saveUser = userRepository.save(request.toEntity(passwordEncoder));
        return new CreateUserResponse(saveUser.getId(), saveUser.getName());
    }


}
