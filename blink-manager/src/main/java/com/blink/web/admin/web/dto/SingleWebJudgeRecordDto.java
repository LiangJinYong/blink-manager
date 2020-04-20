package com.blink.web.admin.web.dto;

import java.time.LocalDateTime;

import com.blink.enumeration.JudgeStatus;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SingleWebJudgeRecordDto {

	private final Long id;
	private final LocalDateTime createdAt;
	private final String hospitalName;
	private final String postcode;
	private final String address;
	private final String addressDetail;
	private final String hospitalTel;
	private final JudgeStatus judgeStatus;
}
