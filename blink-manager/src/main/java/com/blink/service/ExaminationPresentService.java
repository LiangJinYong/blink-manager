package com.blink.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.statistics.hospital.StatisticsDailyHospitalReporitory;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.web.hospital.dto.examinatinPresent.ExaminatinPresentResponseDto;
import com.blink.web.hospital.dto.examinatinPresent.ExaminationPresentHospitalDailyResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ExaminationPresentService {

	private final StatisticsDailyHospitalReporitory statisticsDailyHospitalReporitory;
	private final UserExaminationMetadataRepository metadataRepository;

	public ExaminatinPresentResponseDto getExaminationPresent(Long hospitalId, String searchText, Date startDate, Date endDate,
			Pageable pageable) {

		Page<ExaminationPresentHospitalDailyResponseDto> examinatinPresent = statisticsDailyHospitalReporitory.findByHospitalId(hospitalId, startDate, endDate,
				pageable);
		
		Integer totalExaminationCount = statisticsDailyHospitalReporitory.findTotalExaminationCountForHospital(hospitalId, startDate, endDate);
		Integer totalAgreeYCount = statisticsDailyHospitalReporitory.findTotalAgreeYCountForHospital(hospitalId, startDate, endDate);
		Integer totalAgreeNCount = statisticsDailyHospitalReporitory.findTotalAgreeNCountForHospital(hospitalId, startDate, endDate);
		Integer totalSentCount = statisticsDailyHospitalReporitory.findTotalSentCountForHospital(hospitalId, startDate, endDate);
		Integer totalNonexistConsentForm = metadataRepository.findNonexistConsetFormCountForHospital(hospitalId, startDate, endDate);
		
		ExaminatinPresentResponseDto result = new ExaminatinPresentResponseDto(totalExaminationCount, totalAgreeYCount, totalAgreeNCount, totalSentCount, totalNonexistConsentForm, examinatinPresent);
		return result;
	}

}
