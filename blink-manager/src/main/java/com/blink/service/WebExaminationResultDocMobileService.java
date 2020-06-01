package com.blink.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.examinationResultDocMobile.WebExaminationResultDocMobileRepository;
import com.blink.domain.webfiles.WebFilesRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.admin.web.dto.examinationResultDocMobile.WebExaminationResultDocMobileResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebExaminationResultDocMobileService {

	private final WebExaminationResultDocMobileRepository mobileRepository;
	private final WebFilesRepository webFilesRepository;

	public Page<WebExaminationResultDocMobileResponseDto> getExaminationResultDocMobileList(String searchText,
			SearchPeriod period, Pageable pageable) {
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<WebExaminationResultDocMobileResponseDto> result = mobileRepository.findResultDocMobildList(searchText, time, pageable);

		for (WebExaminationResultDocMobileResponseDto dto : result) {
			String groupId = dto.getGroupId();
			List<WebFileResponseDto> files = webFilesRepository.findByGroupId(groupId);
			dto.setFiles(files);
		}
		return result;
	}

}
