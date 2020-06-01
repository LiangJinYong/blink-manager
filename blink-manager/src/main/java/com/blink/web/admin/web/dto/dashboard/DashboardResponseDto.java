package com.blink.web.admin.web.dto.dashboard;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DashboardResponseDto {
	private final Integer totalExaminationCount;
	private final Integer totalExaminationUserCount;
	private final Integer totalAgreeYCount;
	private final Integer totalAgreeNCount;
	private final Integer nonexistConsetFormCount;
	
	private final Page<DashboardHospitalResponseDto> hospitalList;
}
