package com.blink.web.admin.web.dto.dashboard;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DashboardHospitalResponseDto {

	private final String hospitalName;
	private final Long examinationCount;
	private final Long examinationUserCount;
	private final Long agreeCount;
	private final Long unagreeCount;
	private final Double agreePercent;
	private final Boolean sendAgreeYn;
}
