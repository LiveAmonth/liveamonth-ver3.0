package teamproject.lam_server.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.member.dto.request.FollowRequest;
import teamproject.lam_server.domain.member.dto.response.MemberProfileResponse;
import teamproject.lam_server.domain.member.entity.Follower;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.FollowRepository;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.badrequest.AlreadyFollowMember;
import teamproject.lam_server.exception.notfound.FollowNotFound;
import teamproject.lam_server.exception.notfound.MemberNotFound;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService{
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void follow(FollowRequest request) {
        if(followRepository.findByMemberId(request.getFrom(), request.getTo()).isPresent()){
            throw new AlreadyFollowMember();
        }

        Member from = memberRepository.findById(request.getFrom()).orElseThrow(MemberNotFound::new);
        Member to = memberRepository.findById(request.getTo()).orElseThrow(MemberNotFound::new);

        Follower follower = Follower.builder()
                .from(from)
                .to(to)
                .build();

        followRepository.save(follower);
    }

    @Override
    @Transactional
    public void unFollow(FollowRequest request) {
        Follower follower = followRepository.findByMemberId(request.getFrom(), request.getTo()).orElseThrow(FollowNotFound::new);

        follower.unFollow();
        followRepository.delete(follower);
    }

    @Override
    public Long getFollowersCount(Long to) {
        return followRepository.getFollowersCount(to);
    }
    @Override
    public List<MemberProfileResponse> getFollowers(Long to) {
        return followRepository.getFollowers(to).stream()
                .map(MemberProfileResponse::of)
                .collect(Collectors.toList());
    }
    @Override
    public List<MemberProfileResponse> getFollowings(Long from) {
        return followRepository.getFollowings(from).stream()
                .map(MemberProfileResponse::of)
                .collect(Collectors.toList());
    }
    @Override
    public Long getFollowingsCount(Long from) {
        return followRepository.getFollowingsCount(from);
    }

}
