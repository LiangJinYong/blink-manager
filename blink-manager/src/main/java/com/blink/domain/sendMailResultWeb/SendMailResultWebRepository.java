package com.blink.domain.sendMailResultWeb;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.sendMailResultWeb.SendMailResultWebResponseDto;

public interface SendMailResultWebRepository extends JpaRepository<SendMailResultWeb, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.sendMailResultWeb.SendMailResultWebResponseDto(w.id, w.fileInfo.filekey, w.fileInfo.filename, w.createdAt, w.sentDate, w.sentCount) FROM SendMailResultWeb w WHERE w.createdAt > :time AND w.hospital.id = :hospitalId")
	Page<SendMailResultWebResponseDto> findBySearchTextAndPeriod(@Param("time") LocalDateTime time, @Param("hospitalId") Long hospitalId,
			Pageable pageable); 

}
