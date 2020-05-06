package com.blink.web.admin.web.dto.business;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BusinessResponseDto {

	private final Integer totalAgreeYCount;
	private final Integer totalAgreeNCount;
	private final Integer totalSentCount;
	private final List<SingleBusinessResponseDto> businessList;
}
