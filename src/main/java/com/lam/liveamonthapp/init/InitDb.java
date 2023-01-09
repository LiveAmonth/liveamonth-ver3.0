package com.lam.liveamonthapp.init;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.lam.liveamonthapp.init.service.*;


@Profile("local")
@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitMemberService initMemberService;
    private final InitScheduleService initScheduleService;
    private final InitReviewService initReviewService;
    private final InitCommentService initCommentService;
    private final InitInteractionService initInteractionService;
    private final InitInquiryService initInquiryService;

    //    @PostConstruct
    public void init() {
        initMemberService.initMemberData();
        initScheduleService.initScheduleData();
        initScheduleService.initScheduleContentData();
        initReviewService.initReviewData();
        initCommentService.initScheduleCommentData();
        initCommentService.initReviewCommentData();
        initInteractionService.initInteractionData();
        initInteractionService.initScheduleLikeData();
        initInteractionService.initReviewLikeData();
        initInquiryService.initInquiryData();
    }
}
