package com.blink.web.hospital.dto.examinatinPresent;

import java.time.LocalDate;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ExaminationPresentHospitalDailyResponseDto {
	private final Long hospitalId;
	private final LocalDate date;
	private final Integer examinationCount;
	private final Integer agreeYCount;
	private final Integer agreeNCount;
	private final Integer consentCount;
	private final Integer omissionCost;
	private final Integer sentCost;
	private final Integer paymentAmount;
	private final Integer agreeSendYn;
}
