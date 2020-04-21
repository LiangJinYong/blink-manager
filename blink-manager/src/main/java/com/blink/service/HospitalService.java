package com.blink.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.hospital.HospitalRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.admin.web.dto.hospital.HospitalDetailResponseDto;
import com.blink.web.admin.web.dto.hospital.HospitalResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class HospitalService {

	private final HospitalRepository hospitalRepository;

	// ------------------ ADMIN ------------------
	public HospitalResponseDto getHospitalList(String searchText, SearchPeriod period,
			Pageable pageable) {
		
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<HospitalDetailResponseDto> hospitalList =  hospitalRepository.findBySearchTextAndPeriod(searchText, time, pageable);
		HospitalResponseDto responseDto = new HospitalResponseDto(0, 0, "xxx", hospitalList);
		
		return responseDto;
	}

	// ------------------ HOSPITAL ------------------
	public HospitalDetailResponseDto getHospitalDetail(Long hospitalId) {
		
		HospitalDetailResponseDto responseDto = hospitalRepository.findDetailById(hospitalId);
		return responseDto;
	}

}
