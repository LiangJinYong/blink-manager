package com.blink.web.admin.web.dto.business;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SingleBusinessResponseDto {

	private final Long hospitalId;
	private final LocalDateTime createdAt;
	private final String hospitalName;
	private final Integer agreeYCount;
	private final Integer agreeNCount;
	private final Integer agreeSendYn;
	private final Integer sentCount;
}
