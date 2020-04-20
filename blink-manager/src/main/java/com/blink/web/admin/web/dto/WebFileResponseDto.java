package com.blink.web.admin.web.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WebFileResponseDto {

	private final String fileKey;
	private final String fileName;
	private final String groupFileId;
}
