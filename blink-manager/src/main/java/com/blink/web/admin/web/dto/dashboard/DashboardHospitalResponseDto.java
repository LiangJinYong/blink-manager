package com.blink.web.admin.web.dto.dashboard;

import java.time.LocalDate;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DashboardHospitalResponseDto {
	private final LocalDate date;
	private final Long hospitalId;
	private final String hospitalName;
	private final Integer agreeYCount;
	private final Integer agreeNCount;
	private final Integer examinationCount;
	private final Integer examineeCount;
	private final Integer consentCount;
	private final Integer sentCount;
	private final Integer sentCost;
	private final Integer omissionCost;
	private final Integer agreeSendYn;
	private final Integer paymentAmount;
	private final String programInUse;
	
}
