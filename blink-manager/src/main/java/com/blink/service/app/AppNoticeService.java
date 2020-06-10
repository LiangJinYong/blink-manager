package com.blink.service.app;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.app.notice.AppNotice;
import com.blink.domain.app.notice.AppNoticeRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppNoticeService {

	private final AppNoticeRepository appNoticeRepository;

	public Page<AppNotice> getNoticeList(String searchText, Date startDate, Date endDate, Pageable pageable) {
		
		Page<AppNotice> list = appNoticeRepository.findByNameAndPeriod(searchText, startDate, endDate, pageable);
		return list;
	}

	public void save(String title, String description) {

		AppNotice entity = AppNotice.builder().title(title).description(description).build();
		appNoticeRepository.save(entity);
	}

}
