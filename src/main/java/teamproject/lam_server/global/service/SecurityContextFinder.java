package teamproject.lam_server.global.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.badrequest.IllegalLoggedInMember;
import teamproject.lam_server.exception.badrequest.IllegalWriterOfPost;
import teamproject.lam_server.exception.notfound.MemberNotFound;
import teamproject.lam_server.global.entity.BaseEntity;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityContextFinder {

    private final MemberRepository memberRepository;

    public Member getLoggedInMember() {
        String user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return memberRepository.findByLoginId(user).orElseThrow(MemberNotFound::new);
    }

    public String getLoggedInMemberName() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public <T extends BaseEntity> void checkLegalWriterOfPost(T t) {
        if (!Objects.equals(getLoggedInMember().getLoginId(), t.getCreatedBy())) {
            throw new IllegalWriterOfPost();
        }
    }

    public void checkLegalWriterId(Long id) {
        if (!Objects.equals(getLoggedInMember().getId(), id)) {
            throw new IllegalLoggedInMember();
        }
    }
}
