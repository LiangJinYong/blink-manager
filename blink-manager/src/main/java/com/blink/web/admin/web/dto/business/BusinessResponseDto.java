package com.blink.web.admin.web.dto.business;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BusinessResponseDto {

	private final Integer totalAgreeYCount;
	private final Integer totalAgreeNCount;
	private final Integer totalSentCount;
	private final Page<SingleBusinessResponseDto> businessList;
}
