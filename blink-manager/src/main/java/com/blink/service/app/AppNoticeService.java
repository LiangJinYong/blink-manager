package com.blink.service.app;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.app.notice.AppNotice;
import com.blink.domain.app.notice.AppNoticeRepository;
import com.blink.web.admin.app.dto.AppNoticeSaveRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppNoticeService {

	private final AppNoticeRepository appNoticeRepository;

	public Page<AppNotice> getNoticeList(String title, Integer period, Pageable pageable) {
		LocalDateTime time = LocalDateTime.now();

		switch (period) {
		case 1:
			time = time.minusDays(1);
			break;
		case 2:
			time = time.minusDays(7);
			break;

		case 3:
			time = time.minusDays(30);
			break;
		case 4:
			time = time.minusDays(90);
			break;
		case 5:
			time = time.minusDays(180);
			break;
		case 6:
			time = time.minusDays(365);
			break;
		}
		
		Page<AppNotice> list = appNoticeRepository.findByNameAndPeriod(title, time, pageable);
		return list;
	}

	public void save(AppNoticeSaveRequestDto requestDto) {

		appNoticeRepository.save(requestDto.toEntity());
	}

}
