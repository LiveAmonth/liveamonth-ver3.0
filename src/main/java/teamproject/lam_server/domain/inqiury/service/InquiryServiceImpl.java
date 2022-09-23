package teamproject.lam_server.domain.inqiury.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.auth.jwt.JwtTokenProvider;
import teamproject.lam_server.domain.inqiury.dto.editor.InquiryEditor;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryResponse;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.inqiury.repository.InquiryRepository;
import teamproject.lam_server.domain.member.entity.Member;
import teamproject.lam_server.domain.member.repository.MemberRepository;
import teamproject.lam_server.exception.notfound.InquiryNotFound;
import teamproject.lam_server.global.service.BasicMemberService;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

@Service
@Transactional(readOnly = true)
public class InquiryServiceImpl extends BasicMemberService implements InquiryService {

    private final InquiryRepository inquiryRepository;

    public InquiryServiceImpl(MemberRepository memberRepository,
                              JwtTokenProvider jwtTokenProvider,
                              InquiryRepository inquiryRepository) {
        super(memberRepository, jwtTokenProvider);
        this.inquiryRepository = inquiryRepository;
    }

    @Override
    @Transactional
    public void write(String token, InquiryEditor request) {
        inquiryRepository.save(request.toEntity(getMemberByToken(token)));
    }

    @Override
    public CustomPage<InquiryListResponse> getInquires(String token, PageableDTO pageableDTO) {
        Member member = getMemberByToken(token);
        PageRequest request = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());

        Page<InquiryListResponse> inquiries = inquiryRepository.getInquiries(member.getId(), request)
                .map(InquiryListResponse::of);

        return CustomPage.<InquiryListResponse>builder()
                .page(inquiries)
                .build();
    }

    @Override
    public InquiryResponse getInquiry(Long id) {
        return InquiryResponse.of(inquiryRepository.findById(id).orElseThrow(InquiryNotFound::new));
    }

    @Override
    @Transactional
    public void edit(Long id, InquiryEditor request) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(InquiryNotFound::new);

        InquiryEditor editor = inquiry.toEditor()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .build();

        inquiry.edit(editor);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(InquiryNotFound::new);

        inquiry.remove();
    }
}
