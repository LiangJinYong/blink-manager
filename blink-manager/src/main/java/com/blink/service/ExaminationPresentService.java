package com.blink.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.examinatinPresent.StatisticsDailyRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.hospital.dto.examinatinPresent.ExaminatinPresentHospitalResponseDto;
import com.blink.web.hospital.dto.examinatinPresent.ExaminatinPresentResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ExaminationPresentService {
	
	private final StatisticsDailyRepository examinationPresentRepository;
	
	public ExaminatinPresentResponseDto getExaminationPresent(Long hospitalId, String searchText, SearchPeriod period,
			Pageable pageable) {

		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<ExaminatinPresentHospitalResponseDto> examinatinPresentHospitalList = examinationPresentRepository.findBySearchTextAndPeriod(hospitalId, time, pageable);
		
		ExaminatinPresentResponseDto responseDto = new ExaminatinPresentResponseDto(examinatinPresentHospitalList);
		
		return responseDto;
	}

}
