package teamproject.lam_server.domain.member.service;

import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.dto.editor.PasswordEditor;
import teamproject.lam_server.domain.member.dto.editor.ProfileEditor;
import teamproject.lam_server.domain.member.dto.request.*;
import teamproject.lam_server.domain.member.dto.response.*;
import teamproject.lam_server.global.dto.PostIdResponse;

public interface MemberService{

    /**
     * 회원 가입
     */
    PostIdResponse signUp(SignUpRequest request);

    /**
     * 이메일 중복 체크
     */
    FormCheckResponse checkDuplicateEmail(String email);

    /**
     * 회원 아이디 중복 체크
     */
    FormCheckResponse checkDuplicateLoginId(String LoginId);

    /**
     * 닉네임 중복 체크
     */
    FormCheckResponse checkDuplicateNickname(String nickname);

    /**
     * 회원 아이디 찾기
     */
    FindIdResponse findLoginId(FindIdRequest request);

    /**
     * 회원 비밀번호 찾기
     */
    void findPassword(FindPasswordRequest request);

    /**
     * 비밀번호 재확인
     */
    FormCheckResponse reconfirm(String token, ReconfirmRequest request);

    /**
     * 회원 정보  수정
     */
    void editProfile(String token, ProfileEditor request);

    @Transactional
    void changePassword(String token, PasswordEditor request);

    /**
     * 회원 탈퇴
     */
    void dropUser(String token);

    /**
     * 회원 DB 삭제
     */
    void delete(Long id);

    /**
     * 회원 정보 조회
     */
    MemberProfileResponse getMember(String accessToken);

    /**
     * 메인 화면 프로필
     */
    SimpleProfileResponse getSimpleProfile(String accessToken);
}
