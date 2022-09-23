package teamproject.lam_server.domain.member.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.auth.jwt.JwtTokenProvider;
import teamproject.lam_server.domain.member.dto.editor.PasswordEditor;
import teamproject.lam_server.domain.member.dto.editor.ProfileEditor;
import teamproject.lam_server.domain.member.dto.request.FindIdRequest;
import teamproject.lam_server.domain.member.dto.request.FindPasswordRequest;
import teamproject.lam_server.domain.member.dto.request.ReconfirmRequest;
import teamproject.lam_server.domain.member.dto.request.SignUpRequest;
import teamproject.lam_server.domain.member.dto.response.FindIdResponse;
import teamproject.lam_server.domain.member.dto.response.FormCheckResponse;
import teamproject.lam_server.domain.member.dto.response.MemberProfileResponse;
import teamproject.lam_server.domain.member.dto.response.SimpleProfileResponse;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.badrequest.NotDropMember;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.global.dto.PostIdResponse;
import teamproject.lam_server.global.service.BasicMemberService;
import teamproject.lam_server.mail.dto.TempPasswordSendMailInfo;
import teamproject.lam_server.mail.service.MailService;
import teamproject.lam_server.util.JwtUtil;

import static teamproject.lam_server.global.constants.ResponseMessage.*;

@Service
@Transactional(readOnly = true)
public class MemberServiceImpl extends BasicMemberService implements MemberService{

    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public MemberServiceImpl(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider, MailService mailService, PasswordEncoder passwordEncoder) {
        super(memberRepository, jwtTokenProvider);
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public PostIdResponse signUp(SignUpRequest request) {
        Member saveMember = memberRepository.save(request.toEntity(passwordEncoder));
        return PostIdResponse.of(saveMember.getId());
    }

    @Override
    public MemberProfileResponse getMember(String accessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        return MemberProfileResponse.of(
                memberRepository.findByLoginId(authentication.getName())
                        .orElseThrow(MemberNotFound::new)
        );
    }

    @Override
    public SimpleProfileResponse getSimpleProfile(String accessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        return SimpleProfileResponse.of(
                memberRepository.findByLoginId(authentication.getName())
                        .orElseThrow(MemberNotFound::new)
        );
    }

    @Override
    public FormCheckResponse reconfirm(String token, ReconfirmRequest request) {
        return passwordEncoder.matches(request.getPassword(), getMemberByToken(token).getPassword())
                ? FormCheckResponse.of(true, "", SUCCESS_RECONFIRM)
                : FormCheckResponse.of(false, "", FAIL_RECONFIRM);
    }

    @Override
    @Transactional
    public void editProfile(String token, ProfileEditor request) {
        Member member = getMemberByToken(token);

        ProfileEditor editor = member.toProfileEditor()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .image(request.getImage())
                .build();

        member.editProfile(editor);
    }

    @Override
    @Transactional
    public void changePassword(String token, PasswordEditor request) {
        Member member = getMemberByToken(token);

        PasswordEditor editor = member.toPasswordEditor()
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        member.changePassword(editor);
    }

    @Override
    @Transactional
    public void dropUser(String token) {
        Member member = getMemberByToken(token);

        member.drop();
    }

    @Override
    public FindIdResponse findLoginId(FindIdRequest request) {
        Member findMember = memberRepository.findByNameAndEmail(
                        request.getName(), request.getEmail())
                .orElseThrow(MemberNotFound::new);
        return FindIdResponse.of(findMember);
    }

    @Override
    @Transactional
    public void findPassword(FindPasswordRequest request) {
        // find user with request
        Member member = memberRepository.findByLoginIdAndEmail(request.getLoginId(), request.getEmail())
                .orElseThrow(MemberNotFound::new);

        // create random password
        String tempPassword = JwtUtil.createRandomPassword();

        // update password (temporary password)
        PasswordEditor editor = member.toPasswordEditor()
                .password(passwordEncoder.encode(tempPassword))
                .build();

        member.changePassword(editor);

        // mail send
        mailService.sendMail(TempPasswordSendMailInfo.of(member));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Long queryCount = memberRepository.cleanDeleteById(id);
        if (queryCount == 0) throw new NotDropMember();
    }

    @Override
    public FormCheckResponse checkDuplicateEmail(String email) {
        Boolean isDuplicated = memberRepository.existsByEmail(email);
        return isDuplicated
                ? FormCheckResponse.of(false, email, DUPLICATE_EMAIL)
                : FormCheckResponse.of(true, email, AVAILABLE_EMAIL);
    }

    @Override
    public FormCheckResponse checkDuplicateLoginId(String LoginId) {
        Boolean isDuplicated = memberRepository.existsByLoginId(LoginId);
        return isDuplicated
                ? FormCheckResponse.of(false, LoginId, DUPLICATE_LOGIN_ID)
                : FormCheckResponse.of(true, LoginId, AVAILABLE_LOGIN_ID);
    }

    @Override
    public FormCheckResponse checkDuplicateNickname(String nickname) {
        Boolean isDuplicated = memberRepository.existsByNickname(nickname);
        return isDuplicated
                ? FormCheckResponse.of(false, nickname, DUPLICATE_NICKNAME)
                : FormCheckResponse.of(true, nickname, AVAILABLE_NICKNAME);
    }
}
