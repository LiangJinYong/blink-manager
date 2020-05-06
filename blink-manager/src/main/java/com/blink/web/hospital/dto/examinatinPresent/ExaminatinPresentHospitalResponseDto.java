package com.blink.web.hospital.dto.examinatinPresent;

import java.time.LocalDate;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ExaminatinPresentHospitalResponseDto {
	private final LocalDate date;
	private final Long examinedCount;
	private final Long agreeycount;
	private final Long agreencount;
	/*
	private final Long consentCount;
	private final Long sendCost;
	private final Long omissionCost;
	private final Long paymentAmount;
	private final Boolean sendAgreeYn;
	*/
}
