package teamproject.lam_server.domain.inqiury.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryCreate;
import teamproject.lam_server.domain.inqiury.dto.request.InquiryEdit;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryListResponse;
import teamproject.lam_server.domain.inqiury.dto.response.InquiryResponse;
import teamproject.lam_server.domain.inqiury.entity.Inquiry;
import teamproject.lam_server.domain.inqiury.entity.InquiryEditor;
import teamproject.lam_server.domain.inqiury.repository.InquiryRepository;
import teamproject.lam_server.exception.notfound.InquiryNotFound;
import teamproject.lam_server.global.service.SecurityContextFinder;
import teamproject.lam_server.paging.CustomPage;
import teamproject.lam_server.paging.PageableDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryServiceImpl implements InquiryService {

    private final InquiryRepository inquiryRepository;

    private final SecurityContextFinder finder;

    @Override
    @Transactional
    public void write(InquiryCreate request) {
        inquiryRepository.save(request.toEntity(finder.getLoggedInMember()));
    }

    @Override
    public CustomPage<InquiryListResponse> getInquires(PageableDTO pageableDTO) {
        PageRequest request = PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize());

        Page<InquiryListResponse> inquiries = inquiryRepository.getInquiries(finder.getLoggedInMember().getId(), request)
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
    public void edit(Long id, InquiryEdit request) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(InquiryNotFound::new);

        finder.checkLegalWriterOfPost(inquiry);

        InquiryEditor editor = inquiry.toEditor()
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .build();

        inquiry.edit(editor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(InquiryNotFound::new);

        finder.checkLegalWriterOfPost(inquiry);

        inquiryRepository.delete(inquiry);
    }
}
