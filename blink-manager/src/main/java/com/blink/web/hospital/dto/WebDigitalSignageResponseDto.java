package com.blink.web.hospital.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.blink.enumeration.SignageType;
import com.blink.web.admin.web.dto.WebFileResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class WebDigitalSignageResponseDto {
	private final Long id;
	private final SignageType signageType;
	private final Integer signageNoticeStyle;
	private final String title;
	private final LocalDateTime createdAt;
	private final boolean answerYn;
	private final String questionContent;
	private final String answerContent;
	private final String questionGroupId;
	private final String answerGroupId;
	private final String hospitalName;
	private final String username;
	private final String programInUse;
	private final Long hospitalId;
	private final Integer agreeSendYn;
	
	private List<WebFileResponseDto> questionFiles = new ArrayList<>();
	private List<WebFileResponseDto> answerFiles = new ArrayList<>();

}
