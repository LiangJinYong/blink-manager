package com.blink.web.admin.web.dto.dashboard;

import java.util.Map;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DashboardResponseDto {
	private final Long totalExaminationCount;
	private final Long totalExaminationUserCount;
	private final Long totalAgreeYCount;
	private final Long totalAgreeNCount;
	
	private final Page<Map<String, Object>> hospitalList;
}
