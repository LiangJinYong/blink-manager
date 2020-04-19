package com.blink.web.admin.web.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WebJudgeDetailResponseDto {

	private final String username;
	private final String hospitalName;
	private final LocalDateTime createdAt;
	private final String hospitalTel;
	private final String address;

	private final String fileName;
	private final String fileKey;

	private final String employeeName;
	private final String employeePosition;
	private final String employeeTel;
	private final String employeeEmail;
	private final Integer agreeSendYn;
	private final String programInUse;
}
