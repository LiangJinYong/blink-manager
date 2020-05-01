package com.blink.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.hospitalStatistics.HospitalStatistics;
import com.blink.domain.hospitalStatistics.HospitalStatisticsRepository;
import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.admin.web.dto.dashboard.DashboardResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class DashboardService {

	private final HospitalRepository hospitalRepository;
	private final UserExaminationMetadataDetailRepository detailRepository;
	private final UserExaminationMetadataRepository metadataRepository;
	private final HospitalStatisticsRepository hospitalStatisticsRepository;
	
	public DashboardResponseDto getDashboardData(String searchText, SearchPeriod period, Pageable pageable) {
	
	//	LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<Map<String, Object>> list = hospitalStatisticsRepository.findHospitalData(pageable);

		Map<String, Object> totalCountMap = hospitalStatisticsRepository.findTotalCountMap();
		Long totalExaminationCount = Long.parseLong(totalCountMap.get("totalExaminationCount").toString());
		Long totalExaminationUserCount = Long.parseLong(totalCountMap.get("totalExaminationUserCount").toString());
		Long totalAgreeYCount = Long.parseLong(totalCountMap.get("totalAgreeYCount").toString());
		Long totalAgreeNCount = Long.parseLong(totalCountMap.get("totalAgreeNCount").toString());
		
		DashboardResponseDto responseDto = new DashboardResponseDto(totalExaminationCount, totalExaminationUserCount, totalAgreeYCount, totalAgreeNCount, list);
		
		return responseDto;
	}

	public HospitalStatistics getDashboardDataWithHospital(Long hospitalId) {

		Optional<HospitalStatistics> statistics = hospitalStatisticsRepository.findByHospitalId(hospitalId);
		
		HospitalStatistics result;
		if (statistics.isPresent()) {
			result = statistics.get();
		} else {
			result = new HospitalStatistics();
		}
		
		return result;
	}
}
