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
	private final String hospitalName;
	private final LocalDate dateExamined;
	private final Integer agreeYn;
	private final Integer consentFormExistYn;
	private final String address;
	private final String specialCase;
	private final String ssnPartial;
	private final Integer agreeMail;
	private final Integer agreeSms;
	private final Integer agreeVisit;
	private final Long hospitalId;
	private final String username;
	
	private List<InspectionTypeDto> inspectionTypeList;
}
