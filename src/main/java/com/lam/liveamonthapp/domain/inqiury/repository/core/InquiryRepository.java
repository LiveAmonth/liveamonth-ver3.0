package com.lam.liveamonthapp.domain.inqiury.repository.core;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lam.liveamonthapp.domain.inqiury.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
}
