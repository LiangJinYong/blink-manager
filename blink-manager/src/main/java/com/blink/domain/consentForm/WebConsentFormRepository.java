package com.blink.domain.consentForm;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.domain.hospital.Hospital;
import com.blink.web.admin.web.dto.consentForm.AdminConsentFormDetailResponseDto;
import com.blink.web.hospital.dto.consentForm.HospitalConsentFormDetailResponseDto;

public interface WebConsentFormRepository extends JpaRepository<WebConsentForm, Long> {

	@Query("SELECT new com.blink.web.admin.web.dto.consentForm.AdminConsentFormDetailResponseDto(f.id, f.createdAt, h.displayName, f.receiveDate, f.receiveType, f.receiveTypeText, f.consentYear, f.consentMonth," + 
			"f.count, f.groupId, h.id, h.name, h.programInUse) FROM WebConsentForm f LEFT JOIN f.hospital h LEFT JOIN WebFiles wf ON f.groupId = wf.groupId WHERE (f.hospital.displayName LIKE %:searchText% OR wf.fileName LIKE %:searchText%) AND DATE(f.createdAt) BETWEEN :startDate AND :endDate")
	Page<AdminConsentFormDetailResponseDto> findBySearchTextAndPeriodForAdmin(@Param("searchText") String searchText, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	@Query("SELECT new com.blink.web.admin.web.dto.consentForm.AdminConsentFormDetailResponseDto(f.id, f.createdAt, h.displayName, f.receiveDate, f.receiveType, f.receiveTypeText, f.consentYear, f.consentMonth," + 
			"f.count, f.groupId, h.id, h.name, h.programInUse) FROM WebConsentForm f LEFT JOIN f.hospital h WHERE DATE(f.createdAt) BETWEEN :startDate AND :endDate AND h.id = :hospitalId")
	Page<AdminConsentFormDetailResponseDto> findBySearchTextAndPeriodForHospital(@Param("hospitalId") Long hospitalId, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
			Pageable pageable);

	@Query("SELECT new com.blink.web.hospital.dto.consentForm.HospitalConsentFormDetailResponseDto(f.id, f.createdAt, f.receiveDate, f.receiveType, f.receiveTypeText, f.consentYear, f.consentMonth," + 
			"f.count, f.groupId) FROM WebConsentForm f WHERE DATE(f.createdAt) BETWEEN :startDate AND :endDate AND f.hospital.id = :hospitalId")
	Page<HospitalConsentFormDetailResponseDto> findBySearchTextAndPeriodForHospitalSelf(@Param("hospitalId") Long hospitalId,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	@Query("SELECT SUM(f.count) FROM WebConsentForm f WHERE f.hospital.id = :hospitalId")
	Integer findTotalCountForHospital(@Param("hospitalId") Long hospitalId);

	@Query("SELECT SUM(f.count) FROM WebConsentForm f")
	Integer findTotalCountForAdmin();

	// 통계
	@Query("SELECT COALESCE(SUM(f.count), 0) FROM WebConsentForm f WHERE DATE(f.createdAt) = DATE(:yesterday) AND f.hospital.id = :hospitalId")
	Integer findConsentFormCountSum(@Param("yesterday") LocalDate yesterday, @Param("hospitalId") Long hospitalId);

	// 병원별 대시보디 배송수량
	@Query("SELECT COALESCE(SUM(f.count), 0) FROM WebConsentForm f WHERE f.consentYear = :year AND f.hospital = :hospital")
	Integer findYearlyConsentFormCount(@Param("hospital") Hospital hospital, @Param("year") String year);

}
