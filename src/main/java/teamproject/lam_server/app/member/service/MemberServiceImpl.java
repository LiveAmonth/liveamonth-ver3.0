package teamproject.lam_server.app.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.app.member.domain.Member;
import teamproject.lam_server.app.member.dto.*;
import teamproject.lam_server.app.member.exception.ExistsException;
import teamproject.lam_server.app.member.exception.NormalUserDeleteException;
import teamproject.lam_server.app.member.exception.UserNotFoundException;
import teamproject.lam_server.app.member.repository.MemberCheckRepository;
import teamproject.lam_server.app.member.repository.MemberRepository;
import teamproject.lam_server.app.member.dto.login.LoginMemberRequest;
import teamproject.lam_server.mail.service.MailService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl {

    private final MemberRepository memberRepository;
    private final MemberCheckRepository memberCheckRepository;
    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::new)
                .collect(toList());
    }

    public MemberResponse findOne(Long id) {
        Member findMember = memberRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return new MemberResponse(findMember);
    }

    @Transactional
    public SimpleMemberResponse save(CreateMemberRequest request) {
        Member saveMember = memberRepository.save(request.toEntity(passwordEncoder));
        return new SimpleMemberResponse(saveMember.getId(), saveMember.getName());
    }

    public Member login(LoginMemberRequest request) {

        return memberRepository.findByLoginId(request.getLoginId())
                .filter(m -> passwordEncoder.matches(request.getPassword(), m.getPassword()))
                .orElse(null);
    }

    @Transactional
    public SimpleMemberResponse dropUser(Long id) {
        Member dropMember = memberRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        dropMember.drop();
        return new SimpleMemberResponse(dropMember.getId(), dropMember.getLoginId());
    }

    @Transactional
    public SimpleMemberResponse delete(Long id) {
        Long queryCount = memberRepository.cleanDeleteById(id);
        if (queryCount == 0) throw new NormalUserDeleteException(id);
        return new SimpleMemberResponse(id);
    }

    @Transactional
    public Long modify(Long id, ModifyMemberRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        member.modifyMemberInfo(request.getNickname(), request.getImage());
        return id;
    }

    public DuplicateCheckResponse checkDuplicateEmail(String emailId, String domain) {
        String email = unifyEmail(emailId, domain);
        return checkDuplicateParam(memberCheckRepository.existsByEmail(email), email);
    }

    public DuplicateCheckResponse checkDuplicateLoginId(String LoginId) {
        return checkDuplicateParam(memberCheckRepository.existsByLoginId(LoginId), LoginId);
    }

    public DuplicateCheckResponse checkDuplicateNickname(String nickname) {
        return checkDuplicateParam(memberCheckRepository.existsByEmail(nickname), nickname);
    }


    public SimpleMemberResponse findLoginId(FindLoginIdRequest request) {
        String userLoginId = memberRepository.findLoginIdByNameAndEmail(
                request.getName(),
                unifyEmail(request.getEmail_id(), request.getEmail_domain()))
                .orElseThrow(() -> new UserNotFoundException());
        return new SimpleMemberResponse(userLoginId);
    }

    @Transactional
    public FindPasswordResponse findPassword(FindPasswordRequest request) {
        // unify email
        String email = unifyEmail(request.getEmail_id(), request.getEmail_domain());
        // find user with request
        Member findMember = memberRepository.findByLoginIdAndEmail(request.getLoginId(), email)
                .orElseThrow(() -> new UserNotFoundException());
        // create random password
        String tempPassword = createRandomPassword();
        // update password (temporary password)
        findMember.updatePassword(passwordEncoder.encode(tempPassword));

        // mail send
        FindPasswordResponse response = new FindPasswordResponse(findMember);
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
