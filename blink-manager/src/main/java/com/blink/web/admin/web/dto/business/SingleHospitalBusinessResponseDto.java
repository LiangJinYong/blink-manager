package com.blink.web.admin.web.dto.business;

import java.util.ArrayList;
import java.util.List;

import com.blink.web.admin.web.dto.WebFileResponseDto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SingleHospitalBusinessResponseDto {

	private final Long examinationResultDocId;
	private final String groupId;
	private String createdAt;
	private List<WebFileResponseDto> files = new ArrayList<WebFileResponseDto>();
	private Integer agreeYCount;
	private Integer agreeNCount;
	
}
