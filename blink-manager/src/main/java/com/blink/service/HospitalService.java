package com.blink.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.hospital.HospitalRepository;
import com.blink.web.admin.web.dto.HospitalResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HospitalService {

	private final HospitalRepository hospitalRepo;

	public Map<String, Object> getHospitalListInfo(String displayName, Pageable pageable) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Page<HospitalResponseDto> findByDisplayName = hospitalRepo.findByDisplayNameContaining(displayName, pageable);
		result.put("hospitalListPage", findByDisplayName);
		
//		result.put("hospitalCount", hospitalListPage.getTotalElements());
		result.put("totalExaminationCount", 1234);
		result.put("mostExamHospital", "국민건강내과의원");
		return result;
	}

}
