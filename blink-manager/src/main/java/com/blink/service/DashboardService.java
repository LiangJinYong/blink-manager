package com.blink.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blink.domain.consentForm.WebConsentFormRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.hospitalStatistics.HospitalStatisticsRepository;
import com.blink.domain.joinContact.JoinContactRepository;
import com.blink.domain.qna.WebQnaRepository;
import com.blink.domain.signage.WebDigitalSignageRepository;
import com.blink.domain.statistics.ageGender.StatisticsDailyAgeGenderRepository;
import com.blink.domain.statistics.examination.StatisticsDailyExaminationRepository;
import com.blink.domain.statistics.hospital.StatisticsDailyHospitalReporitory;
import com.blink.domain.user.UserExaminationMetadataDetailRepository;
import com.blink.domain.user.UserExaminationMetadataRepository;
import com.blink.enumeration.SearchPeriod;
import com.blink.util.CommonUtils;
import com.blink.web.admin.web.dto.dashboard.DashboardHospitalResponseDto;
import com.blink.web.admin.web.dto.dashboard.DashboardResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class DashboardService {

	private final HospitalRepository hospitalRepository;
	private final UserExaminationMetadataDetailRepository detailRepository;
	private final UserExaminationMetadataRepository metadataRepository;
	private final WebConsentFormRepository consentFormRepository;
	private final HospitalStatisticsRepository hospitalStatisticsRepository;
	
	private final StatisticsDailyHospitalReporitory statisticsDailyHospitalReporitory;
	private final StatisticsDailyAgeGenderRepository statisticsDailyAgeGenderRepository;
	private final StatisticsDailyExaminationRepository statisticsDailyExaminationRepository;
	
	private final WebQnaRepository webQnaRepository;
	private final WebDigitalSignageRepository webDigitalSignageRepository;
	private final JoinContactRepository joinContactRepository;
	
	
	public DashboardResponseDto getDashboardData(String searchText, SearchPeriod period, Pageable pageable) {
	
		LocalDateTime time = CommonUtils.getSearchPeriod(period);
		
		Page<DashboardHospitalResponseDto> dailyHospital = statisticsDailyHospitalReporitory.findBySearchTextAndPeriod(searchText, time, pageable);

		Integer totalExaminationCount = statisticsDailyHospitalReporitory.findTotalExaminationCount(time);
		Integer totalExamineeCount = statisticsDailyHospitalReporitory.findTotalExamineeCount(time);
		Integer totalAgreeYCount = statisticsDailyHospitalReporitory.findTotalAgreeYCount(time);
		Integer totalAgreeNCount = statisticsDailyHospitalReporitory.findTotalAgreeNCount(time);
		Integer nonexistConsetFormCount = metadataRepository.findNonexistConsetFormCountForAdmin(time);
		DashboardResponseDto responseDto = new DashboardResponseDto(totalExaminationCount, totalExamineeCount, totalAgreeYCount, totalAgreeNCount, nonexistConsetFormCount, dailyHospital);
		
		return responseDto;
	}

	public Map<String, Object> getDashboardDataWithHospital(Long hospitalId, Integer year, Integer month, Pageable pageable) {

		Map<String, Object> result = new HashMap<String, Object>();
		
		Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(() -> new IllegalArgumentException("No such hospital"));
		
		String hospitalName = hospital.getName();
		
//		List<PieChartResult> ageGrChartResults = statisticsService.findAgeGroupChartResult(hospitalName);
//		result.put("ageGroupChartResult", ageGrChartResults);
//		
//		List<PieChartResult> genderGroupChartResult = statisticsService.findGenderGroupChartResult(hospitalName);
//		result.put("genderGroupChartResult", genderGroupChartResult);
//		
//		List<PieChartResult> inspectionTypeGroupChartResult = statisticsService.findInspectionTypeGroupChartResult(hospitalName);
//		result.put("inspectionTypeGroupChartResult", inspectionTypeGroupChartResult);
		
		// 연령/성별 그래프
		Object[] countForAgeAndGender = statisticsDailyAgeGenderRepository.findCountForAgeAndGender(hospital, year);
		Object[] ageAndGenderArr = (Object[]) countForAgeAndGender[0];
		
		Map<String, Object> ageGenderItem = new HashMap<>();
		
		for(int i = 0; i < ageAndGenderArr.length; i++) {
			
			ageGenderItem.put((i >= 7 ? "fe" : "") + "male" + (i % 7 + 2) + "0Count", ageAndGenderArr[i]);
		}
		
		result.put("statisticsDailyAgeGender", ageGenderItem);
		
		// 검진종류별 그래프
		Object[] countForExamination = statisticsDailyExaminationRepository.findCountForExamination(hospital, year);
		Object[] examinationArr = (Object[]) countForExamination[0];
		
		Map<String, Object> examinationItem = new HashMap<>();
		
		examinationItem.put("cancerBreastCount", examinationArr[0]);
		examinationItem.put("cancerColonCount", examinationArr[1]);
		examinationItem.put("cancerColonSecondCount", examinationArr[2]);
		examinationItem.put("cancerLiverCount", examinationArr[3]);
		examinationItem.put("cancerLiverSecondCount", examinationArr[4]);
		examinationItem.put("cancerLugCount", examinationArr[5]);
		examinationItem.put("cancerStomachCount", examinationArr[6]);
		examinationItem.put("cancerWombCount", examinationArr[7]);
		examinationItem.put("firstGeneralCount", examinationArr[8]);
		examinationItem.put("firstLifeChangeCount", examinationArr[9]);
		examinationItem.put("secondLifeHabitCount", examinationArr[10]);
		examinationItem.put("outpatientFirstCount", examinationArr[11]);
		examinationItem.put("outpatientCancerCount", examinationArr[12]);
		
		result.put("statisticsExamination", examinationItem);
		// 요일별 
		List<Map<String, Integer>> weeklyExamineeList = new ArrayList<Map<String,Integer>>();
		for(int i = 1; i <= 7; i++) {
			Map<String, Integer> weeklyExaminee = new HashMap<>();
			weeklyExaminee.put("dayOfWeek", i);
			
			Integer examineeCount = statisticsDailyHospitalReporitory.findExamineeCountByHospitalAndDayOfWeek(hospital, year, i);
			weeklyExaminee.put("examineeCount", examineeCount);
			
			weeklyExamineeList.add(weeklyExaminee);
		}
		
		// 월별
		List<Map<String, Integer>> monthlyExamineeList = new ArrayList<Map<String,Integer>>();

		LocalDate date = new LocalDate(year, month, 1);
		
		for(int i = 1; i <= 4; i++) {
			Map<String, Integer> monthlyExaminee = new HashMap<>();
			
			Integer examineeCount = statisticsDailyHospitalReporitory.findExamineeCountByHospitalAndMonth(hospital, date.getYear(),  date.getMonthOfYear());
			
			monthlyExaminee.put("year", date.getYear());
			monthlyExaminee.put("month", date.getMonthOfYear());
			monthlyExaminee.put("examineeCount", examineeCount);
			
			monthlyExamineeList.add(monthlyExaminee);
			date = date.minusMonths(1);
		}
		
		result.put("weeklyExamineeList", weeklyExamineeList);
		result.put("monthlyExamineeList", monthlyExamineeList);
		
		Integer yearlyTotalExaminationCount = detailRepository.findYearlyTotalExaminationCount(hospitalId, year);
		Integer yearlyAgreeYCount = metadataRepository.findYearlyAgreeYnCount(hospitalId, year, 1);
		Integer yearlyAgreeNCount = metadataRepository.findYearlyAgreeYnCount(hospitalId, year, 0);
		Integer yearlyConsentFormCount = consentFormRepository.findYearlyConsentFormCount(hospital, String.valueOf(year));
		
		result.put("totalExaminationCount", yearlyTotalExaminationCount);
		result.put("totalAgreeYCount", yearlyAgreeYCount);
		result.put("totalAgreeNCount", yearlyAgreeNCount);
		result.put("totalConsentFormCount", yearlyConsentFormCount);
		
		return result;
	}

	public Map<String, Boolean> getUnprocessedData() {
		Map<String, Boolean> result = new HashMap<>();
		
		Integer unansweredQnaCount = webQnaRepository.findCountByAnswerYn(false);
		Integer unansweredDigitalSignageCount = webDigitalSignageRepository.findCountByAnswerYn(false);
		Long unansweredJoinContactCount = joinContactRepository.findCountByAnswerYn(false);
		
		result.put("hasUnprocessedQna", unansweredQnaCount > 0);
		result.put("hasUnprocessedDigitalSignage", unansweredDigitalSignageCount > 0);
		result.put("hasUnprocessedJoinContact", unansweredJoinContactCount > 0);
		
		return result;
	}

	public Map<String, Boolean> getUnreadData(Long hospitalId) {
		Map<String, Boolean> result = new HashMap<>();
		
		Integer unreadQnaCount = webQnaRepository.findCountByUnread(hospitalId);
		Integer unreadDigitalSignageCount = webDigitalSignageRepository.findCountByUnread(hospitalId);
		
		result.put("unreadQnaCount", unreadQnaCount > 0);
		result.put("unreadDigitalSignageCount", unreadDigitalSignageCount > 0);
		
		return result;
	}
}
