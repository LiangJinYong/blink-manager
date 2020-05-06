package com.blink.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.web.admin.web.dto.business.BusinessResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class BusinessManageService {
	
	private final UserExaminationMetadataRepository metadataRepository;
	private final HospitalRepository hospitalRepository;
	
	public BusinessResponseDto getBusinessData(String searchText, String searchDate, Pageable pageable) {
		
		Page<BigInteger> hospitalIds = metadataRepository.findBusinessHospitalIds(searchText, searchDate, pageable);
		
		List<BigInteger> content = hospitalIds.getContent();
		
		for(BigInteger hospitalIdBigInteger: content) {
			Long hospitalId = hospitalIdBigInteger.longValue();
			Integer agreeYCount = metadataRepository.findAgreeYnCount(searchDate, hospitalId, 1);
			Integer agreeNCount = metadataRepository.findAgreeYnCount(searchDate, hospitalId, 0);
			Optional<Hospital> hospital = hospitalRepository.findById(hospitalId);
			String hospitalName = hospital.get().getDisplayName();
			Integer agreeSendYn = hospital.get().getAgreeSendYn();
			
		}
		System.out.println(hospitalIds);
		return null;
	}

}
