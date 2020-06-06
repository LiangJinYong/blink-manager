package com.blink.web.admin.web.dto.examinationResultDocMobile;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WebExaminationResultDocMobileResponseDto {

	private final Integer totalCount;
	private final Integer newlyRegisterCount;
	private final Integer waitingCount;
	private final Integer completedCount;
	
	private final Page<SingleWebExaminationResultDocMobileResponseDto> resultDocMobileList;
}
