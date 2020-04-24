package com.blink.domain.examinationResultDoc;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.webExaminationResultDoc.WebExaminationResultDocResponseDto;

public interface WebExaminationResultDocRepository extends JpaRepository<WebExaminationResultDoc, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.webExaminationResultDoc.WebExaminationResultDocResponseDto(d.id, d.createdAt, d.groupId) FROM WebExaminationResultDoc d WHERE d.createdAt >= :time AND d.hospital.id = :hospitalId")
	Page<WebExaminationResultDocResponseDto> findBySearchTextAndPeriodWithHospital(@Param("time") LocalDateTime time, @Param("hospitalId") Long hospitalId,
			Pageable pageable);
	

}