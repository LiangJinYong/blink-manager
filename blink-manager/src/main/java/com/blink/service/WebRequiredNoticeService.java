package com.blink.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.blink.domain.requiredNotice.WebRequiredNotice;
import com.blink.domain.requiredNotice.WebRequiredNoticeRepository;
import com.blink.web.admin.web.dto.WebRequiredNoticeSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebRequiredNoticeService {

	private final WebRequiredNoticeRepository webRequiredNoticeRepository;

	public Optional<WebRequiredNotice> getRecentRequiredNotice() {
		Optional<WebRequiredNotice> webRequiredNotice = webRequiredNoticeRepository.findRecentRequiredNotice();
		return webRequiredNotice;
	}

	public void insertRequiredNotice(WebRequiredNoticeSaveRequestDto dto) {

		webRequiredNoticeRepository.save(dto.toEntity());
	}
}
