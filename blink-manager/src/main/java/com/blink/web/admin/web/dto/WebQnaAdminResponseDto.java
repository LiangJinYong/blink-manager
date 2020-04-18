package com.blink.web.admin.web.dto;

import org.springframework.data.domain.Page;

import com.blink.enumeration.QuestionType;
import com.blink.web.hospital.dto.WebQnaResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WebQnaAdminResponseDto {

	private final Page<WebQnaResponseDto> webQnaList;
	private final long totalCount;
	private final int waitingCount;
	private final int completedCount;
	private final QuestionType questionTypeMost;
}
