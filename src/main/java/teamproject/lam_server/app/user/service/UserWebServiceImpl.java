package teamproject.lam_server.app.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.CreateUserRequest;
import teamproject.lam_server.app.user.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import static teamproject.lam_server.constants.AttrConstants.*;

@Service
@RequiredArgsConstructor
public class UserWebServiceImpl implements UserWebService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(String loginId, String password) {
        return userRepository.findUserByLoginId(loginId)
                .filter(m -> passwordEncoder.matches(password, m.getPassword()))
                .orElse(null);
    }

    @Override
    public User findId(String name, String email) {
        return userRepository.findUserByNameAndEmail(name, email)
                .orElse(null);
    }

    @Override
    public Map<String, Object> findPw(String loginId, String email) {
        return this.editPassword(userRepository.findUserByLoginIdAndEmail(loginId, email).orElse(null));
    }

    private Map<String, Object> editPassword(User user) {
        if (user == null) return null;
        else {
            Map<String, Object> result = new HashMap<>();
            String temporaryPw = "";
            for (int i = 0; i < 8; i++) {
                temporaryPw += (char) ((Math.random() * 26) + 97);
            }
            userRepository.editPassword(user, passwordEncoder.encode(temporaryPw));
            result.put(EMAIL, user.getEmail());
            result.put(NAME, user.getName());
            result.put(TEMPORARY_PW, temporaryPw);
            return result;
        }
    }

    @Override
    public Long save(CreateUserRequest createUserRequest) {
        return userRepository.save(createUserRequest.toEntity(passwordEncoder)).getId();
    }

    @Override
    public void editUserImage(Long id, String image) {
        userRepository.editImage(id, image);
    }

    @Override
    public User find(long id) {
        return userRepository.findById(id).orElse(null);
    }
}
