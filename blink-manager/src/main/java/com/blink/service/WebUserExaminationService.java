package com.blink.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.hospital.dto.userExamination.InspectionTypeDto;
import com.blink.web.hospital.dto.userExamination.SingleUserExaminationResponseDto;
import com.blink.web.hospital.dto.userExamination.UserExaminationResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class WebUserExaminationService {
	
	private final UserExaminationMetadataRepository metadataRepository;
	private final UserExaminationMetadataDetailRepository detailRepository;
	
	public UserExaminationResponseDto getUserExamination(Long hospitalId, String searchText, SearchPeriod period,
			Pageable pageable) {

		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<SingleUserExaminationResponseDto> list = metadataRepository.findBySearchTextAndPeriodWithHospital(hospitalId, searchText, time, pageable);
		
		List<SingleUserExaminationResponseDto> content = list.getContent();
		
		for(SingleUserExaminationResponseDto dto : content) {
			List<InspectionTypeDto> inspectionTypeList = detailRepository.findInspectionTypeByMetadata(dto.getUserExaminationId());
			dto.setInspectionTypeList(inspectionTypeList);
		}
		
		UserExaminationResponseDto responseDto = new UserExaminationResponseDto(list);
		
		return responseDto;
	}

}
