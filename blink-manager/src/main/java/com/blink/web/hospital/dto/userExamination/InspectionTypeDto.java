package com.blink.web.hospital.dto.userExamination;

import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class InspectionTypeDto {

	private final InspectionType inspectionType;
	private final InspectionSubType inspectionSubType;
	private final String filekey;
	private final String filename;
}
