package teamproject.lam_server.global.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import teamproject.lam_server.domain.member.constants.Role;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.badrequest.IllegalLoggedInMember;
import teamproject.lam_server.exception.badrequest.IllegalWriterOfPost;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.global.entity.BaseEntity;

import java.util.Collection;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityContextFinder {

    private final MemberRepository memberRepository;

    public Member getLoggedInMember() {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByLoginId(user).orElseThrow(MemberNotFound::new);
    }

    public String getAuthenticationName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public <T extends BaseEntity> void checkLegalWriterOfPost(T t) {
        if (!Objects.equals(getAuthenticationName(), t.getCreatedBy())) {
            throw new IllegalWriterOfPost();
        }
    }

    public Member checkLegalWriterId(Long id) {
        Member loggedInMember = getLoggedInMember();
        if (!Objects.equals(loggedInMember.getId(), id)) {
            throw new IllegalLoggedInMember();
        }
        return loggedInMember;
    }

    public void checkLegalLoginId(String loginId) {
        if (!Objects.equals(getAuthenticationName(), loginId)) {
            throw new IllegalLoggedInMember();
        }
    }
}
