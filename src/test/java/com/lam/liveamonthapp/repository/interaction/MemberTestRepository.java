package com.lam.liveamonthapp.repository.interaction;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lam.liveamonthapp.domain.member.entity.Member;

public interface MemberTestRepository extends JpaRepository<Member, Long> {

}
