package com.blink.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.sendMailResultWeb.SendMailResultWebRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.hospital.dto.sendMailResultWeb.SendMailResultWebResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class SendMailResultWebService {

	private final SendMailResultWebRepository sendMailResultWebRepository;

	public Page<SendMailResultWebResponseDto> getSendMailResultWeb(String searchText, SearchPeriod period,
			Pageable pageable, Long hospitalId) {

		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<SendMailResultWebResponseDto> sendMailResultWebList = sendMailResultWebRepository.findBySearchTextAndPeriod(time, hospitalId, pageable);
		return sendMailResultWebList;
	}
	
}
