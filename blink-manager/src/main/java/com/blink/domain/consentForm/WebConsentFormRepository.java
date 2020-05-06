package com.blink.domain.consentForm;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.consentForm.AdminConsentFormDetailResponseDto;
import com.blink.web.hospital.dto.consentForm.HospitalConsentFormDetailResponseDto;

public interface WebConsentFormRepository extends JpaRepository<WebConsentForm, Long> {

	@Query("SELECT new com.blink.web.admin.web.dto.consentForm.AdminConsentFormDetailResponseDto(f.id, f.createdAt, h.displayName, f.receiveDate, f.receiveType, f.receiveTypeText, f.consentYear, f.consentMonth," + 
			"f.count, f.groupId, h.id, h.name) FROM WebConsentForm f LEFT JOIN f.hospital h WHERE f.createdAt > :time")
	Page<AdminConsentFormDetailResponseDto> findBySearchTextAndPeriodForAdmin(@Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT new com.blink.web.admin.web.dto.consentForm.AdminConsentFormDetailResponseDto(f.id, f.createdAt, h.displayName, f.receiveDate, f.receiveType, f.receiveTypeText, f.consentYear, f.consentMonth," + 
			"f.count, f.groupId, h.id, h.name) FROM WebConsentForm f LEFT JOIN f.hospital h WHERE f.createdAt > :time AND h.id = :hospitalId")
	Page<AdminConsentFormDetailResponseDto> findBySearchTextAndPeriodForHospital(@Param("hospitalId") Long hospitalId, @Param("time") LocalDateTime time,
			Pageable pageable);

	@Query("SELECT new com.blink.web.hospital.dto.consentForm.HospitalConsentFormDetailResponseDto(f.id, f.createdAt, f.consentYear, f.consentMonth," + 
			"f.count, f.groupId) FROM WebConsentForm f WHERE f.createdAt > :time AND f.hospital.id = :hospitalId")
	Page<HospitalConsentFormDetailResponseDto> findBySearchTextAndPeriodForHospitalSelf(@Param("hospitalId") Long hospitalId,
			@Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT SUM(f.count) FROM WebConsentForm f WHERE f.hospital.id = :hospitalId")
	Integer findTotalCountForHospital(@Param("hospitalId") Long hospitalId);

	@Query("SELECT SUM(f.count) FROM WebConsentForm f")
	Integer findTotalCountForAdmin();

}
