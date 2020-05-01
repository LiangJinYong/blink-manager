package com.blink.web.admin.web.dto.consentForm;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ConsentFormResponseDto {

	private final Integer consentFormCount;
	private final Page<AdminConsentFormDetailResponseDto> consentFormList;
}
