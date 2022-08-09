package teamproject.lam_server.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.interaction.entity.Follower;
import teamproject.lam_server.domain.interaction.repository.FollowRepository;
import teamproject.lam_server.domain.interaction.service.InteractionServiceImpl;
import teamproject.lam_server.domain.member.dto.request.InteractionRequest;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@ActiveProfiles({"local", "oauth2"})
@Rollback
@Slf4j
public class FollowServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    InteractionServiceImpl followService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FollowRepository followRepository;

    @Test
    @DisplayName("회원1이 회원2를 팔로우 한다.")
    @Rollback
    void test1() {
        // given
        // json data | member1 : rbdus7174 | member2 : kxuxeon
        Member member1 = memberRepository.findByLoginId("rbdus7174").get();
        Member member2 = memberRepository.findByLoginId("kxuxeon").get();

        // when
        InteractionRequest request = new InteractionRequest();
        request.setFrom(member1.getId());
        request.setTo(member2.getId());

        followService.follow(request);

        System.out.println("========시작==========================");
        Follower savedFollower = followRepository.findByMemberId(request.getFrom(), request.getTo()).get();
        System.out.println("========끝==========================");

        // then
        Assertions.assertThat(member1.getFollowing()).contains(savedFollower);
        Assertions.assertThat(member2.getFollowers()).contains(savedFollower);
    }

    @Test
    @DisplayName("회원1이 회원2를 언팔로우 한다.")
    @Rollback
    void test2() {
        // given
        // json data | member1 : rbdus7174 | member2 : kxuxeon
        Member member1 = memberRepository.findByLoginId("rbdus7174").get();
        Member member2 = memberRepository.findByLoginId("kxuxeon").get();

        InteractionRequest request = new InteractionRequest();
        request.setFrom(member1.getId());
        request.setTo(member2.getId());

        followService.follow(request);
        Follower savedFollower = followRepository.findByFromAndTo(member1, member2).get();

        // when
        followService.unFollow(request);

        // then
        Assertions.assertThat(member1.getFollowing().contains(savedFollower)).isFalse();
        Assertions.assertThat(member2.getFollowers().contains(savedFollower)).isFalse();
    }
}
