package teamproject.lam_server.app.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.*;
import teamproject.lam_server.app.user.exception.NormalUserDeleteException;
import teamproject.lam_server.app.user.exception.UserNotFoundException;
import teamproject.lam_server.app.user.repository.UserRepository;
import teamproject.lam_server.validator.JoinUserValidator;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final JoinUserValidator joinUserValidator;

    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .collect(toList());
    }

    public UserResponse findOne(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return new UserResponse(findUser);
    }

    @Transactional
    public SimpleUserResponse save(CreateUserRequest request) {
        User saveUser = userRepository.save(request.toEntity(passwordEncoder));
        return new SimpleUserResponse(saveUser.getId(), saveUser.getName());
    }

    public User login(LoginUserRequest request) {
        return userRepository.findByLoginId(request.getLoginId())
                .filter(m -> passwordEncoder.matches(request.getPassword(), m.getPassword()))
                .orElse(null);
    }

    @Transactional
    public SimpleUserResponse dropUser(Long id) {
        User dropUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        dropUser.drop();
        return new SimpleUserResponse(dropUser.getId(), dropUser.getLoginId());
    }

    @Transactional
    public SimpleUserResponse delete(Long id) {
        Long queryCount = userRepository.cleanDeleteById(id);
        if (queryCount == 0) throw new NormalUserDeleteException(id);
        return new SimpleUserResponse(id);
    }

    @Transactional
    public Long modify(Long id, ModifyUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.modifyUserInfo(request.getNickname(), request.getImage());
        return id;
    }
}
