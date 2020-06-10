package com.blink.domain.statistics.hospital;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.domain.examinatinPresent.StatisticsDailyId;
import com.blink.domain.hospital.Hospital;
import com.blink.web.admin.web.dto.dashboard.DashboardHospitalResponseDto;
import com.blink.web.hospital.dto.examinatinPresent.ExaminationPresentHospitalDailyResponseDto;

public interface StatisticsDailyHospitalReporitory extends JpaRepository<StatisticsDailyHospital, StatisticsDailyId> {

	@Query("SELECT new com.blink.web.hospital.dto.examinatinPresent.ExaminationPresentHospitalDailyResponseDto(h.id, sdh.date, sdh.examinationCount, sdh.agreeYCount, sdh.agreeNCount, sdh.consentCount, sdh.omissionCost, sdh.sentCost, sdh.paymentAmount, h.agreeSendYn) FROM StatisticsDailyHospital sdh JOIN sdh.hospital h WHERE sdh.hospital.id = :hospitalId AND DATE(sdh.date) BETWEEN :startDate AND :endDate")
	Page<ExaminationPresentHospitalDailyResponseDto> findByHospitalId(@Param("hospitalId") Long hospitalId, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	@Query("SELECT new com.blink.web.admin.web.dto.dashboard.DashboardHospitalResponseDto(h.id, h.displayName, SUM(dh.agreeYCount) AS agreeYCount, SUM(dh.agreeNCount) AS agreeNCount, SUM(dh.examinationCount) AS examinationCount, SUM(dh.examineeCount) AS examineeCount, SUM(dh.consentCount) AS consentCount, SUM(dh.sentCount) AS sentCount, SUM(dh.sentCost) AS sentCost, SUM(dh.omissionCost) AS omissionCost, h.agreeSendYn, SUM(dh.paymentAmount) AS paymentAmount, h.programInUse, h.name) FROM StatisticsDailyHospital dh JOIN dh.hospital h WHERE h.displayName LIKE %:searchText% AND DATE(dh.date) BETWEEN :startDate AND :endDate GROUP BY dh.hospital")
	Page<DashboardHospitalResponseDto> findBySearchTextAndPeriod(@Param("searchText") String searchText, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	@Query("SELECT SUM(h.examinationCount) FROM StatisticsDailyHospital h WHERE DATE(h.date) BETWEEN :startDate AND :endDate")
	Integer findTotalExaminationCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT SUM(h.examineeCount) FROM StatisticsDailyHospital h WHERE DATE(h.date) BETWEEN :startDate AND :endDate")
	Integer findTotalExamineeCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT SUM(h.agreeYCount) FROM StatisticsDailyHospital h WHERE DATE(h.date) BETWEEN :startDate AND :endDate")
	Integer findTotalAgreeYCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT SUM(h.agreeNCount) FROM StatisticsDailyHospital h WHERE DATE(h.date) BETWEEN :startDate AND :endDate")
	Integer findTotalAgreeNCount(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("SELECT SUM(h.examinationCount) FROM StatisticsDailyHospital h WHERE DATE(h.date) BETWEEN :startDate AND :endDate AND h.hospital.id = :hospitalId")
	Integer findTotalExaminationCountForHospital(@Param("hospitalId") Long hospitalId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("SELECT SUM(h.agreeYCount) FROM StatisticsDailyHospital h WHERE DATE(h.date) BETWEEN :startDate AND :endDate AND h.hospital.id = :hospitalId")
	Integer findTotalAgreeYCountForHospital(@Param("hospitalId") Long hospitalId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query("SELECT SUM(h.agreeNCount) FROM StatisticsDailyHospital h WHERE DATE(h.date) BETWEEN :startDate AND :endDate AND h.hospital.id = :hospitalId")
	Integer findTotalAgreeNCountForHospital(@Param("hospitalId") Long hospitalId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT SUM(h.sentCount) FROM StatisticsDailyHospital h WHERE DATE(h.date) BETWEEN :startDate AND :endDate AND h.hospital.id = :hospitalId")
	Integer findTotalSentCountForHospital(@Param("hospitalId") Long hospitalId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

	// 병원 대시보드 요일별 검진자수
	@Query("SELECT SUM(h.examineeCount) FROM StatisticsDailyHospital h WHERE h.hospital = :hospital AND h.year = :year AND h.dayOfWeek = :dayOfWeek")
	Integer findExamineeCountByHospitalAndDayOfWeek(@Param("hospital") Hospital hospital, @Param("year") Integer year, @Param("dayOfWeek") Integer dayOfWeek);

	// 병원 대시보드 월별 검진자수
	@Query("SELECT SUM(h.examineeCount) FROM StatisticsDailyHospital h WHERE h.hospital = :hospital AND h.year = :year AND h.month = :month")
	Integer findExamineeCountByHospitalAndMonth(@Param("hospital") Hospital hospital, @Param("year") Integer year, @Param("month") Integer month);

}
