package com.blink.web.hospital.dto.consentForm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.blink.enumeration.ReceiveType;
import com.blink.web.admin.web.dto.WebFileResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class HospitalConsentFormDetailResponseDto {

	private final Long consentFormId;
	private final LocalDateTime createdAt;
	private final LocalDate receiveDate;
	private final ReceiveType receiveType;
	private final String receiveTypeText;
	private final String consentYear;
	private final String consentMonth;
	private final Long count;
	private final String groupId;
	
	private List<WebFileResponseDto> files = new ArrayList<>();
}
