package teamproject.lam_server.app.user.service;

import groovy.lang.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.user.domain.User;
import teamproject.lam_server.app.user.dto.*;
import teamproject.lam_server.app.user.exception.ExistsException;
import teamproject.lam_server.app.user.exception.NormalUserDeleteException;
import teamproject.lam_server.app.user.exception.UserNotFoundException;
import teamproject.lam_server.app.user.repository.UserCheckRepository;
import teamproject.lam_server.app.user.repository.UserRepository;
import teamproject.lam_server.mail.service.MailService;
import teamproject.lam_server.validator.JoinUserValidator;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final UserCheckRepository userCheckRepository;
    private final MailService mailService;

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

    public DuplicateCheckResponse checkDuplicateEmail(String emailId, String domain) {
        String email = unifyEmail(emailId, domain);
        return checkDuplicateParam(userCheckRepository.existsByEmail(email), email);
    }

    public DuplicateCheckResponse checkDuplicateLoginId(String LoginId) {
        return checkDuplicateParam(userCheckRepository.existsByLoginId(LoginId), LoginId);
    }

    public DuplicateCheckResponse checkDuplicateNickname(String nickname) {
        return checkDuplicateParam(userCheckRepository.existsByEmail(nickname), nickname);
    }


    public SimpleUserResponse findLoginId(FindLoginIdRequest request) {
        String userLoginId = userRepository.findLoginId(
                request.getName(),
                unifyEmail(request.getEmail_id(), request.getEmail_domain()))
                .orElseThrow(() -> new UserNotFoundException());
        return new SimpleUserResponse(userLoginId);
    }

    @Transactional
    public FindPasswordResponse findPassword(FindPasswordRequest request) {
        // unify email
        String email = unifyEmail(request.getEmail_id(), request.getEmail_domain());
        // find user with request
        User findUser = userRepository.findByLoginIdAndEmail(request.getLoginId(), email)
                .orElseThrow(() -> new UserNotFoundException());
        // create random password
        String tempPassword = createRandomPassword();
        // update password (temporary password)
        findUser.updatePassword(passwordEncoder.encode(tempPassword));

        // mail send
        FindPasswordResponse response = new FindPasswordResponse(findUser);
        mailService.sendMail(response);
        return response;
    }

    private String createRandomPassword() {
        int index = 0;
        char[] charArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z'};

        StringBuffer tempPassword = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            index = (int) (charArr.length * Math.random());
            tempPassword.append(charArr[index]);
        }
        return tempPassword.toString();
    }

    private DuplicateCheckResponse checkDuplicateParam(Boolean result, String param) {
        if (result == null) throw new ExistsException(param);
        return new DuplicateCheckResponse(result, param);
    }

    private String unifyEmail(String emailId, String domain) {
        return emailId + "@" + domain;
    }
}
