package com.blink.web.hospital.dto.webSign;

import java.time.LocalDateTime;

import com.blink.web.admin.web.dto.WebFileResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
public class WebSignResponseDto {

	private final Long id;
	private final String doctorName;
	private final String doctorPhone;
	private final String doctorId;
	private final String license;
	private final String groupId;
	private final LocalDateTime createdAt;
	private WebFileResponseDto file;
}
