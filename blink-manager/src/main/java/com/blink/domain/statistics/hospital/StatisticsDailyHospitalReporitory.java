package com.blink.domain.statistics.hospital;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.domain.examinatinPresent.StatisticsDailyId;
import com.blink.domain.hospital.Hospital;
import com.blink.web.admin.web.dto.dashboard.DashboardHospitalResponseDto;

public interface StatisticsDailyHospitalReporitory extends JpaRepository<StatisticsDailyHospital, StatisticsDailyId> {

	@Query("SELECT h FROM StatisticsDailyHospital h WHERE h.hospital.id = :hospitalId AND h.date >= DATE(:time)  ORDER BY h.date DESC")
	Page<StatisticsDailyHospital> findByHospitalId(@Param("hospitalId") Long hospitalId, @Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT new com.blink.web.admin.web.dto.dashboard.DashboardHospitalResponseDto(dh.date, h.id, h.displayName, dh.agreeYCount, dh.agreeNCount, dh.examinationCount, dh.examineeCount, dh.consentCount, dh.sentCount, dh.sentCost, dh.omissionCost, dh.agreeSendYn, dh.paymentAmount, h.programInUse, h.name) FROM StatisticsDailyHospital dh JOIN dh.hospital h WHERE h.displayName LIKE %:searchText% AND dh.date >= DATE(:time)  ORDER BY dh.date DESC")
	Page<DashboardHospitalResponseDto> findBySearchTextAndPeriod(@Param("searchText") String searchText, @Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT SUM(h.examinationCount) FROM StatisticsDailyHospital h WHERE h.date >= DATE(:time)")
	Integer findTotalExaminationCount(@Param("time") LocalDateTime time);

	@Query("SELECT SUM(h.examineeCount) FROM StatisticsDailyHospital h WHERE h.date >= DATE(:time)")
	Integer findTotalExamineeCount(@Param("time") LocalDateTime time);

	@Query("SELECT SUM(h.agreeYCount) FROM StatisticsDailyHospital h WHERE h.date >= DATE(:time)")
	Integer findTotalAgreeYCount(@Param("time") LocalDateTime time);

	@Query("SELECT SUM(h.agreeNCount) FROM StatisticsDailyHospital h WHERE h.date >= DATE(:time)")
	Integer findTotalAgreeNCount(@Param("time") LocalDateTime time);
	
	@Query("SELECT SUM(h.examinationCount) FROM StatisticsDailyHospital h WHERE h.date >= DATE(:time) AND h.hospital.id = :hospitalId")
	Integer findTotalExaminationCountForHospital(@Param("hospitalId") Long hospitalId, @Param("time") LocalDateTime time);
	
	@Query("SELECT SUM(h.agreeYCount) FROM StatisticsDailyHospital h WHERE h.date >= DATE(:time) AND h.hospital.id = :hospitalId")
	Integer findTotalAgreeYCountForHospital(@Param("hospitalId") Long hospitalId, @Param("time") LocalDateTime time);
	
	@Query("SELECT SUM(h.agreeNCount) FROM StatisticsDailyHospital h WHERE h.date >= DATE(:time) AND h.hospital.id = :hospitalId")
	Integer findTotalAgreeNCountForHospital(@Param("hospitalId") Long hospitalId, @Param("time") LocalDateTime time);

	@Query("SELECT SUM(h.sentCount) FROM StatisticsDailyHospital h WHERE h.date >= DATE(:time) AND h.hospital.id = :hospitalId")
	Integer findTotalSentCountForHospital(@Param("hospitalId") Long hospitalId, @Param("time") LocalDateTime time);

	// 병원 대시보드 요일별 검진자수
	@Query("SELECT SUM(h.examineeCount) FROM StatisticsDailyHospital h WHERE h.hospital = :hospital AND h.year = :year AND h.dayOfWeek = :dayOfWeek")
	Integer findExamineeCountByHospitalAndDayOfWeek(@Param("hospital") Hospital hospital, @Param("year") Integer year, @Param("dayOfWeek") Integer dayOfWeek);

	// 병원 대시보드 월별 검진자수
	@Query("SELECT SUM(h.examineeCount) FROM StatisticsDailyHospital h WHERE h.hospital = :hospital AND h.year = :year AND h.month = :month")
	Integer findExamineeCountByHospitalAndMonth(@Param("hospital") Hospital hospital, @Param("year") Integer year, @Param("month") Integer month);

}
