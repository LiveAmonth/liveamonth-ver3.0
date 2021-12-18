package teamproject.lam_simple.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import teamproject.lam_simple.domain.User;
import teamproject.lam_simple.dto.UserForm;
import teamproject.lam_simple.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void save(UserForm userForm) {
        userRepository.save(userForm.toEntity(passwordEncoder));
    }

    public void editUserImage(Long id, String image) {
        userRepository.editImage(id, image);
    }

    public User find(long id) {
        return userRepository.findById(id).orElse(null);
    }
}
