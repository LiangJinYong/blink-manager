package com.blink.web.admin.app.dto.userExamination;

import java.time.LocalDate;

import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MobileResultDocMetadataDetailResponseDto {

	private final InspectionType inspectionType;
	private final InspectionSubType inspectionSubType;
	private final LocalDate dateExamined;
	private final String doctorName;
	private final String doctorLicenseNumber;
	private final String inspectionPlace;
	private final LocalDate dateInterpreted;
}
