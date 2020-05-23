package com.blink.web.admin.web.dto.business;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SingleBusinessResponseDto {

	private final Long hospitalId;
	private final String createdAt;
	private final String hospitalName;
	private final Integer agreeYCount;
	private final Integer agreeNCount;
	private final Integer agreeSendYn;
	private final Integer sentCount;
	private final String username;
	
}
