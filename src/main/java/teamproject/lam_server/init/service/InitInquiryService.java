package teamproject.lam_server.init.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.inqiury.dto.editor.InquiryEditor;
import teamproject.lam_server.domain.inqiury.repository.InquiryRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.util.JsonUtil;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitInquiryService {

    private static final String INQUIRY = "inquiry";
    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void initInquiryData() {
        Member member = memberRepository.findAll().get(0);
        inquiryRepository.saveAll(
                JsonUtil.jsonArrayToList(INQUIRY, InquiryEditor.class).stream()
                        .map(inquiryEditor -> inquiryEditor.toEntity(member))
                        .collect(Collectors.toList())
        );
    }
}
