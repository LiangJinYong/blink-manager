package com.blink.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.examinatinPresent.StatisticsDailyRepository;
import com.blink.domain.statistics.hospital.StatisticsDailyHospital;
import com.blink.domain.statistics.hospital.StatisticsDailyHospitalReporitory;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.hospital.dto.examinatinPresent.ExaminatinPresentResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class ExaminationPresentService {

	private final StatisticsDailyRepository examinationPresentRepository;
	private final StatisticsDailyHospitalReporitory statisticsDailyHospitalReporitory;
	private final UserExaminationMetadataRepository metadataRepository;

	public ExaminatinPresentResponseDto getExaminationPresent(Long hospitalId, String searchText, SearchPeriod period,
			Pageable pageable) {

		LocalDateTime time = CommonUtils.getSearchPeriod(period);

		Page<StatisticsDailyHospital> examinatinPresent = statisticsDailyHospitalReporitory.findByHospitalId(hospitalId, time,
				pageable);
		
		Integer totalExaminationCount = statisticsDailyHospitalReporitory.findTotalExaminationCountForHospital(hospitalId, time);
		Integer totalAgreeYCount = statisticsDailyHospitalReporitory.findTotalAgreeYCountForHospital(hospitalId, time);
		Integer totalAgreeNCount = statisticsDailyHospitalReporitory.findTotalAgreeNCountForHospital(hospitalId, time);
		Integer totalSentCount = statisticsDailyHospitalReporitory.findTotalSentCountForHospital(hospitalId, time);
		Integer totalNonexistConsentForm = metadataRepository.findNonexistConsetFormCountForHospital(hospitalId, time);
		
		ExaminatinPresentResponseDto result = new ExaminatinPresentResponseDto(totalExaminationCount, totalAgreeYCount, totalAgreeNCount, totalSentCount, totalNonexistConsentForm, examinatinPresent);
		return result;
	}

}
