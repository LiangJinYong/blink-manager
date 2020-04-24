package com.blink.web.hospital.dto.webExaminationResultDoc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.blink.web.admin.web.dto.WebFileResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@RequiredArgsConstructor
public class WebExaminationResultDocResponseDto {

	private final Long id;
	private final LocalDateTime createdAt;
	private final String groupId;
	private List<WebFileResponseDto> files = new ArrayList<>();
}
