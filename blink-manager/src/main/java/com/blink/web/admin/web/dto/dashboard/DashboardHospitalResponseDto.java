package com.blink.web.admin.web.dto.dashboard;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DashboardHospitalResponseDto {

	private final Long hospitalId;
	private final String hospitalName;
	private final Long examinationCount;
	private final Long examinationUserCount;
	private final Long agreeYCount;
	private final Long agreeNCount;
	private final Boolean sendAgreeYn;
}
