package com.blink.domain.examinationResultDoc;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.domain.hospital.Hospital;
import com.blink.web.admin.web.dto.business.SingleBusinessResponseDto;
import com.blink.web.admin.web.dto.business.SingleHospitalBusinessResponseDto;
import com.blink.web.hospital.dto.webExaminationResultDoc.WebExaminationResultDocResponseDto;

public interface WebExaminationResultDocRepository extends JpaRepository<WebExaminationResultDoc, Long> {

	@Query("SELECT DISTINCT new com.blink.web.hospital.dto.webExaminationResultDoc.WebExaminationResultDocResponseDto(d.id, d.createdAt, d.groupId) FROM WebExaminationResultDoc d LEFT JOIN WebFiles f ON d.groupId = f.groupId WHERE f.fileName LIKE %:searchText% AND DATE(d.createdAt) BETWEEN :startDate AND :endDate AND d.hospital.id = :hospitalId")
	Page<WebExaminationResultDocResponseDto> findBySearchTextAndPeriodWithHospital(@Param("searchText") String searchText, @Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("hospitalId") Long hospitalId,
			Pageable pageable);

	@Query("SELECT new com.blink.web.admin.web.dto.business.SingleHospitalBusinessResponseDto(d.id, d.groupId, d.createdAt) FROM WebExaminationResultDoc d WHERE DATE(d.createdAt) BETWEEN :startDate AND :endDate AND d.hospital = :hospital")
	Page<SingleHospitalBusinessResponseDto> findByCreatedAtAndHospital(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("hospital") Hospital hospital,
			Pageable pageable);
	
	@Query("SELECT new com.blink.web.admin.web.dto.business.SingleBusinessResponseDto(h.id, d.createdAt, h.displayName, h.agreeSendYn, d.groupId, h.name, h.programInUse) FROM WebExaminationResultDoc d JOIN d.hospital h WHERE DATE(d.createdAt) BETWEEN :startDate AND :endDate AND h.displayName LIKE %:searchText%")
	Page<SingleBusinessResponseDto> findBySearchTextAndPeriodForBusiness(@Param("searchText") String searchText, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
}