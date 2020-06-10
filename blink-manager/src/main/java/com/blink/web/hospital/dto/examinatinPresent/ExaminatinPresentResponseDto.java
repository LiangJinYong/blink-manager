package com.blink.web.hospital.dto.examinatinPresent;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ExaminatinPresentResponseDto {

	private final Integer totalExaminationCount;
	private final Integer totalAgreeYCount;
	private final Integer totalAgreeNCount;
	private final Integer totalSentCount;
	private final Integer totalNonexistConsentForm;
	
	private final Page<ExaminationPresentHospitalDailyResponseDto> examinatinPresentHospitalList; 
}
