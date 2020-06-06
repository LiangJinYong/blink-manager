package com.blink.web.admin.web.dto.examinationResultDocMobile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.blink.domain.user.UserExaminationEntireDataOfOne;
import com.blink.domain.user.UserExaminationMetadata;
import com.blink.enumeration.GenderType;
import com.blink.web.admin.app.dto.userExamination.MobileResultDocMetadataDetailResponseDto;
import com.blink.web.admin.web.dto.WebFileResponseDto;
import com.blink.web.hospital.dto.userExamination.InspectionTypeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SingleWebExaminationResultDocMobileResponseDto {

	private final Long id;
	private final LocalDateTime createdAt;
	private final String username;
	private final String ssnPartial;
	private final String phone;
	private final String hospitalName;
	private final String hospitalAddress;
	private final String hospitalPostcode;
	private final Integer status;
	private final String groupId;
	private final GenderType gender;
	
	private Integer displayGender;
	private String birthday;
	private Integer nationality;
	private UserExaminationEntireDataOfOne userExaminationEntireDataOfOne;
	private Long hospitalDataId;
	private String userAddress;
	
	private List<WebFileResponseDto> files = new ArrayList<>();
	private List<MobileResultDocMetadataDetailResponseDto> metadataDetailList = new ArrayList<>();
}
