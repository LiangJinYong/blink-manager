package com.blink.web.admin.web.dto.dashboard;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DashboardHospitalResponseDto {
	private final Long hospitalId;
	private final String hospitalName;
	private final Long agreeYCount;
	private final Long agreeNCount;
	private final Long examinationCount;
	private final Long examineeCount;
	private final Long consentCount;
	private final Long sentCount;
	private final Long sentCost;
	private final Long omissionCost;
	private final Integer agreeSendYn;
	private final Long paymentAmount;
	private final String programInUse;
	private final String username;
}
