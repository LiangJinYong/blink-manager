package com.blink.web.hospital.dto.examinatinPresent;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ExaminatinPresentResponseDto {

	private final Page<ExaminatinPresentHospitalResponseDto> examinatinPresentHospitalList; 
}
