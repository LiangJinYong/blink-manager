package com.blink.domain.sendMailResultWeb;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.sendMailResultWeb.SendMailResultWebResponseDto;

public interface SendMailResultWebRepository extends JpaRepository<SendMailResultWeb, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.sendMailResultWeb.SendMailResultWebResponseDto(w.id, w.fileInfo.filekey, w.fileInfo.filename, w.createdAt, w.sentDate, w.sentCount, w.totalCount) FROM SendMailResultWeb w WHERE w.createdAt > :time AND w.hospital.id = :hospitalId")
	Page<SendMailResultWebResponseDto> findBySearchTextAndPeriod(@Param("time") LocalDateTime time,
			@Param("hospitalId") Long hospitalId, Pageable pageable);

	@Query(value = "SELECT id pdfWebId, filename FROM pdf_web WHERE hospital_id = :hospitalId ORDER BY id DESC", nativeQuery = true)
	List<Map<Long, String>> findPdfInfoList(@Param("hospitalId") Long hospitalId);

}
