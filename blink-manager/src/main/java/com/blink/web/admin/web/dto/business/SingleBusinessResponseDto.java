package com.blink.web.admin.web.dto.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.blink.web.admin.web.dto.WebFileResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SingleBusinessResponseDto {

	private final Long hospitalId;
	private final LocalDateTime createdAt;
	private final String hospitalName;
	private final Integer agreeSendYn;
	private final String groupId;
	private final String username;
	private final String programInUse;
	private List<WebFileResponseDto> files = new ArrayList<>();
	
}
