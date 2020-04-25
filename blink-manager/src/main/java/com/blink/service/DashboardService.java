package com.blink.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class DashboardService {

	private final HospitalRepository hospitalRepository;
	private final UserExaminationMetadataDetailRepository detailRepository;
	private final UserExaminationMetadataRepository metadataRepository;
	
	public void getDashboardData(String searchText, SearchPeriod period, Pageable pageable) {
	
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
	}
}
