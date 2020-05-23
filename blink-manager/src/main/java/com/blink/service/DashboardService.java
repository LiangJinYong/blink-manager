package com.blink.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.hospitalStatistics.HospitalStatisticsRepository;
import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.service.StatisticsService.PieChartResult;
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
	
	private final StatisticsService statisticsService;
	
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

	public Map<String, Object> getDashboardDataWithHospital(Long hospitalId) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new IllegalArgumentException("No such hospital"));
		
		String hospitalName = hospital.getName();
		
		List<PieChartResult> ageGrChartResults = statisticsService.findAgeGroupChartResult(hospitalName);
		result.put("ageGroupChartResult", ageGrChartResults);
		
		List<PieChartResult> genderGroupChartResult = statisticsService.findGenderGroupChartResult(hospitalName);
		result.put("genderGroupChartResult", genderGroupChartResult);
		
		List<PieChartResult> inspectionTypeGroupChartResult = statisticsService.findInspectionTypeGroupChartResult(hospitalName);
		result.put("inspectionTypeGroupChartResult", inspectionTypeGroupChartResult);
		
		return result;
	}
}
