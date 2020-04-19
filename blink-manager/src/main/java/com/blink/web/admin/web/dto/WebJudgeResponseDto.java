package com.blink.web.admin.web.dto;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WebJudgeResponseDto {

	private final long totalJudgeCount;
	private final long waitingJudgeCount;
	private final long approvedJudgeCount;
	private final long deniedJudgeCount;
	
	private final Page<SingleWebJudgeRecordDto> webJudgeList;
}
