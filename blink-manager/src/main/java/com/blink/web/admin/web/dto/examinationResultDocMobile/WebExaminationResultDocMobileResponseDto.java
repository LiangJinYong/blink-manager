package com.blink.web.admin.web.dto.examinationResultDocMobile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.blink.web.admin.web.dto.WebFileResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WebExaminationResultDocMobileResponseDto {

	private final Long id;
	private final LocalDateTime createdAt;
	private final String username;
	private final String birthday;
	private final String phone;
	private final String hospitalName;
	private final Integer status;
	private final String groupId;
	
	private List<WebFileResponseDto> files = new ArrayList<>();
}
