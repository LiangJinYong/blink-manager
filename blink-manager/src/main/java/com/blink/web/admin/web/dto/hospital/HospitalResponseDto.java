package com.blink.web.admin.web.dto.hospital;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class HospitalResponseDto {

	private final Integer hospitalCount;
	private final Integer diagnoseCount;
	private final String mostExaminationHospitalName;
	private final Page<HospitalDetailResponseDto> hospitalList;
}
