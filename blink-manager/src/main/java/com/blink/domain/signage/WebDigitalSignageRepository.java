package com.blink.domain.signage;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.WebDigitalSignageResponseDto;

public interface WebDigitalSignageRepository extends JpaRepository<WebDigitalSignage, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.WebDigitalSignageResponseDto(s.id, s.signageType, s.signageNoticeStyle, s.title, s.createdAt, s.answerYn, s.questionContent, s.answerContent, s.questionGroupId, s.answerGroupId, h.displayName, h.name, h.programInUse, h.id, h.agreeSendYn) FROM WebDigitalSignage s JOIN s.hospital h WHERE title LIKE %:searchText% AND DATE(s.createdAt) BETWEEN :startDate AND :endDate AND s.hospital.id = :hospitalId")
	Page<WebDigitalSignageResponseDto> findByTitleAndPeriodWithHospital(@Param("searchText") String searchText, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("hospitalId") Long hospitalId, Pageable pageable);

	@Query("SELECT new com.blink.web.hospital.dto.WebDigitalSignageResponseDto(s.id, s.signageType, s.signageNoticeStyle, s.title, s.createdAt, s.answerYn, s.questionContent, s.answerContent, s.questionGroupId, s.answerGroupId, h.displayName, h.name, h.programInUse, h.id, h.agreeSendYn) FROM WebDigitalSignage s JOIN s.hospital h WHERE title LIKE %:searchText% AND DATE(s.createdAt) BETWEEN :startDate AND :endDate")
	Page<WebDigitalSignageResponseDto> findByTitleAndPeriodWithAdmin(@Param("searchText") String searchText, @Param("startDate") Date startDate, @Param("endDate") Date endDate,
			Pageable pageable);

	@Query("SELECT COUNT(s.answerYn) FROM WebDigitalSignage s WHERE s.answerYn = :answerYn")
	int findCountByAnswerYn(@Param("answerYn") boolean answerYn);

	@Modifying
	@Query("UPDATE WebDigitalSignage s SET s.readAnswerYn = true WHERE s.answerYn = true AND s.hospital.id = :hospitalId")
	void updateReadAnswerStatus(@Param("hospitalId") Long hospitalId);

	@Query("SELECT COUNT(*) FROM WebDigitalSignage s WHERE s.hospital.id = :hospitalId AND s.answerYn = true AND s.readAnswerYn = false")
	Integer findCountByUnread(@Param("hospitalId") Long hospitalId);

}
