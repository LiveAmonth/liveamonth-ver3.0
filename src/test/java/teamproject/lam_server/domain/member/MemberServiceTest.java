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
import teamproject.lam_server.domain.member.dto.request.FollowRequest;
import teamproject.lam_server.domain.member.entity.Followers;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.FollowRepository;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.domain.member.service.MemberServiceImpl;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
@ActiveProfiles({"local","oauth2"})
@Rollback
@Slf4j
public class MemberServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberServiceImpl memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FollowRepository followRepository;

    @Test
    @DisplayName("회원1이 회원2를 팔로우 한다.")
    void test1() {
        // given
        // json data | member1 : rbdus7174 | member2 : kxuxeon
        Member member1 = memberRepository.findByLoginId("rbdus7174").get();
        Member member2 = memberRepository.findByLoginId("kxuxeon").get();

        // when
        FollowRequest request = new FollowRequest();
        request.setFrom(member1.getLoginId());
        request.setTo(member2.getLoginId());

        memberService.follow(request);

        System.out.println("========시작==========================");
        Followers savedFollowers = followRepository.findByLoginId(request).get();
        System.out.println("========끝==========================");

        // then
        Assertions.assertThat(member1.getFollowing()).contains(savedFollowers);
        Assertions.assertThat(member2.getFollowers()).contains(savedFollowers);
    }

    @Test
    @DisplayName("회원1이 회원2를 언팔로우 한다.")
    void test2() {
        // given
        // json data | member1 : rbdus7174 | member2 : kxuxeon
        Member member1 = memberRepository.findByLoginId("rbdus7174").get();
        Member member2 = memberRepository.findByLoginId("kxuxeon").get();

        FollowRequest request = new FollowRequest();
        request.setFrom(member1.getLoginId());
        request.setTo(member2.getLoginId());

        memberService.follow(request);
        Followers savedFollowers = followRepository.findByFromAndTo(member1, member2).get();

        // when
        memberService.unFollow(request);

        // then
//        Assertions.assertThat(followRepository.count()).isEqualTo(0);
        Assertions.assertThat(member1.getFollowing().contains(savedFollowers)).isFalse();
        Assertions.assertThat(member2.getFollowers().contains(savedFollowers)).isFalse();
    }
}
