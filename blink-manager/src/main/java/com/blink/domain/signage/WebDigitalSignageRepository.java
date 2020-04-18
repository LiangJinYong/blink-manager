package com.blink.domain.signage;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.WebDigitalSignageResponseDto;

public interface WebDigitalSignageRepository extends JpaRepository<WebDigitalSignage, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.WebDigitalSignageResponseDto(s.id, s.signageType, s.title, s.createdAt, s.answerYn, s.questionContent, s.answerContent, s.questionGroupId, s.answerGroupId, h.displayName) FROM WebDigitalSignage s JOIN s.hospital h WHERE title LIKE %:title% AND s.createdAt >= :time AND s.hospital.id = :hospitalId")
	Page<WebDigitalSignageResponseDto> findByTitleAndPeriodWithHospital(@Param("title") String title, @Param("time") LocalDateTime time,
			@Param("hospitalId") Long hospitalId, Pageable pageable);

	@Query("SELECT new com.blink.web.hospital.dto.WebDigitalSignageResponseDto(s.id, s.signageType, s.title, s.createdAt, s.answerYn, s.questionContent, s.answerContent, s.questionGroupId, s.answerGroupId, h.displayName) FROM WebDigitalSignage s JOIN s.hospital h WHERE title LIKE %:title% AND s.createdAt >= :time")
	Page<WebDigitalSignageResponseDto> findByTitleAndPeriodWithAdmin(@Param("title") String title, @Param("time") LocalDateTime time,
			Pageable pageable);

	@Query("SELECT COUNT(s.answerYn) FROM WebDigitalSignage s WHERE s.answerYn = :answerYn")
	int findByAnswerYn(@Param("answerYn") boolean b);

}
