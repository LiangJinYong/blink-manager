package com.blink.web.hospital.dto.userExamination;

import java.time.LocalDate;
import java.util.List;

import com.blink.enumeration.Gender;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SingleUserExaminationResponseDto {

	private final Long userExaminationId;
	private final String name;
	private final Gender gender;
	private final LocalDate birthday;
	private final String phone;
	private final LocalDate dateExamined;
	private final Integer agreeYn;
	private final Integer consentFormExistYn;
	private final String address;
	private final String specialCase;
	
	private List<InspectionTypeDto> inspectionTypeList;
}
