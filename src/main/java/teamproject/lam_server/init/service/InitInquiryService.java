package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.inqiury.dto.editor.InquiryEditor;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.util.JsonUtil;

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
                " (created_date, last_modified_date, created_by, last_modified_by, category, content, is_answered, is_removed, title, inquiry_answer_id, member_id)" +
                " values (now(), now(), :created_by, :last_modified_by, :category, :content, false, false, :title, null, :member_id)";

        List<InquiryEditor> requests = JsonUtil.jsonArrayToList(INQUIRY, InquiryEditor.class);
        for (InquiryEditor request : requests) {
            em.createNativeQuery(
                            query)
                    .setParameter("created_by", member.getLoginId())
                    .setParameter("last_modified_by", member.getLoginId())
                    .setParameter("category", request.getCategory().getCode())
                    .setParameter("content", request.getContent())
                    .setParameter("title", request.getTitle())
                    .setParameter("member_id", member.getId())
                    .executeUpdate();
        }
    }
}
