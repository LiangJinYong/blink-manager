package com.blink.web.admin.web.dto.dashboard;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DashboardResponseDto {

	private final Long totalExaminationCount;
	private final Long totalExaminationUserCount;
	private final Long totalAgreeCount;
	private final Long totalUnagreeCount;
	private final Double totalAgreePercent;
	private final Long totalSendAgreeCount;
	
	private final Page<DashboardHospitalResponseDto> hospitalList;
}
