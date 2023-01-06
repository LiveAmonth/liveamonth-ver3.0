package com.lam.liveamonthapp.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.lam.liveamonthapp.domain.inqiury.dto.request.InquiryCreate;
import com.lam.liveamonthapp.domain.member.entity.Member;
import com.lam.liveamonthapp.domain.member.repository.core.MemberRepository;
import com.lam.liveamonthapp.util.JsonUtil;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitInquiryService {
    private static final String INQUIRY = "inquiry";
    private final MemberRepository memberRepository;
    private final EntityManager em;
    @Transactional
    public void initInquiryData() {
        Member member = memberRepository.findAll().get(0);
        String query = "insert into inquiry" +
                " (created_date, last_modified_date, created_by, last_modified_by, category, content, is_answered, title, inquiry_answer_id, member_id)" +
                " values (now(), now(), :created_by, :last_modified_by, :category, :content, false, :title, null, :member_id)";

        List<InquiryCreate> requests = JsonUtil.jsonArrayToList(INQUIRY, InquiryCreate.class);
        for (InquiryCreate request : requests) {
            em.createNativeQuery(
                            query)
                    .setParameter("created_by", member.getLoginId())
                    .setParameter("last_modified_by", member.getLoginId())
                    .setParameter("category", request.getCategory())
                    .setParameter("content", request.getContent())
                    .setParameter("title", request.getTitle())
                    .setParameter("member_id", member.getId())
                    .executeUpdate();
        }
    }
}
