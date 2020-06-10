package com.blink.domain.sendMailResultWeb;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.sendMailResultWeb.SendMailResultWebResponseDto;

public interface SendMailResultWebRepository extends JpaRepository<SendMailResultWeb, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.sendMailResultWeb.SendMailResultWebResponseDto(w.id, w.fileInfo.filekey, w.fileInfo.filename, w.createdAt, w.sentDate, w.uploadDate, w.sentCount) FROM SendMailResultWeb w WHERE DATE(w.createdAt) BETWEEN :startDate AND :endDate AND w.hospital.id = :hospitalId")
	Page<SendMailResultWebResponseDto> findBySearchTextAndPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("hospitalId") Long hospitalId, Pageable pageable);

	@Query(value = "SELECT id pdfWebId, filename FROM pdf_web WHERE hospital_id = :hospitalId ORDER BY id DESC", nativeQuery = true)
	List<Map<Long, String>> findPdfInfoList(@Param("hospitalId") Long hospitalId);

	// 업무 관리
//	@Query("SELECT COALESCE(SUM(w.sentCount), 0) FROM SendMailResultWeb w WHERE w.sentDate >= DATE(:searchLocalDate) AND w.hospital = :hospital")
//	Integer findSentCountBySentDateAndHospital(@Param("searchLocalDate") LocalDateTime searchLocalDate, @Param("hospital") Hospital hospital);

//	@Query("SELECT COALESCE(SUM(w.sentCount), 0) FROM SendMailResultWeb w WHERE w.sentDate >= DATE(:searchLocalDate)")
//	Integer findTotalSentCount(@Param("searchLocalDate") LocalDate searchLocalDate);

	// 통계
	@Query("SELECT COALESCE(SUM(w.sentCount), 0) FROM SendMailResultWeb w WHERE DATE(w.createdAt) = DATE(:yesterday) AND w.hospital.id = :hospitalId")
	Integer findSentCountSum(@Param("yesterday") LocalDate yesterday, @Param("hospitalId") Long hospitalId);

}
