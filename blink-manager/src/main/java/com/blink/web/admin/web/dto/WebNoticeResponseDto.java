package com.blink.web.admin.web.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WebNoticeResponseDto {
	private final Long id;
	private final String title;
	private final String description;
	private final LocalDateTime createdAt;
	private final String groupId;
	private List<WebFileResponseDto> files = new ArrayList<>();
	
}
