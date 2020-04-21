package com.blink.web.admin.web.dto.hospital;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class HospitalDetailResponseDto {
	
	private final Long id;
	private final String hospitalName;
	private final String username;
	private final LocalDateTime regDate;
	private final String employeeName;
	private final String hospitalTel;
	private final String employeeTel;
	private final Integer agreeSendYn;
	private final String postcode;
	private final String address;
	private final String addressDetail;
	private final String employeeEmail;
	private final String programInUse;
	private final Integer signagesStand;
	private final Integer signagesMountable;
	private final Integer signagesExisting;
	private final String fileName;
	private final String fileKey;
	private final String groupFileId;
	private final String employeePosition;
}
