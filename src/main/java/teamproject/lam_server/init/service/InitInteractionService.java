package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.dto.InteractionRequest;
import teamproject.lam_server.domain.interaction.repository.member.FollowRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitInteractionService {
    private final FollowRepository followRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void initInteractionData() {
        List<Member> members = memberRepository.findAll();
        Member fromMember = members.get(0);
        for (int i = 1; i < members.size(); i++) {
            InteractionRequest request = new InteractionRequest();

            request.setFrom(fromMember.getId());
            request.setTo(members.get(i).getId());
            followRepository.follow(fromMember.getLoginId(), request);

            request.setFrom(members.get(i).getId());
            request.setTo(fromMember.getId());
            followRepository.follow(members.get(i).getLoginId(), request);
        }
    }
}
