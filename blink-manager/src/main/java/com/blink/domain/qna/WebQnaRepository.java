package com.blink.domain.qna;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.WebQnaResponseDto;


public interface WebQnaRepository extends JpaRepository<WebQna, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.WebQnaResponseDto(q.id, q.questionType, q.title, q.createdAt, q.answerYn, q.questionContent, q.answerContent, q.questionGroupId, q.answerGroupId) FROM WebQna q WHERE title LIKE %:title% AND createdAt >= :time AND q.hospital.id = :hospitalId")
	Page<WebQnaResponseDto> findByTitleAndPeriodWithHospital(@Param("title") String title, @Param("time") LocalDateTime time, @Param("hospitalId") Long hospitalId, Pageable pageable); 

}
