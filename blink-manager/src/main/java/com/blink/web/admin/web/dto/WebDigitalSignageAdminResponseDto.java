package com.blink.web.admin.web.dto;

import org.springframework.data.domain.Page;

import com.blink.web.hospital.dto.WebDigitalSignageResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WebDigitalSignageAdminResponseDto {

	private final Page<WebDigitalSignageResponseDto> webQnaList;
	private final long totalCount;
	private final int waitingCount;
	private final int completedCount;
}
