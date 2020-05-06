package com.blink.domain.qna;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.enumeration.QuestionType;
import com.blink.web.hospital.dto.WebQnaResponseDto;

public interface WebQnaRepository extends JpaRepository<WebQna, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.WebQnaResponseDto(q.id, q.questionType, q.title, q.createdAt, q.answerYn, q.questionContent, q.answerContent, q.questionGroupId, q.answerGroupId, h.displayName, h.name) FROM WebQna q JOIN q.hospital h WHERE title LIKE %:searchText% AND q.createdAt >= :time AND q.hospital.id = :hospitalId")
	Page<WebQnaResponseDto> findByTitleAndPeriodWithHospital(@Param("searchText") String searchText,
			@Param("time") LocalDateTime time, @Param("hospitalId") Long hospitalId, Pageable pageable);

	@Query("SELECT new com.blink.web.hospital.dto.WebQnaResponseDto(q.id, q.questionType, q.title, q.createdAt, q.answerYn, q.questionContent, q.answerContent, q.questionGroupId, q.answerGroupId, h.displayName, h.name) FROM WebQna q JOIN q.hospital h WHERE title LIKE %:searchText% AND q.createdAt >= :time")
	Page<WebQnaResponseDto> findByTitleAndPeriodWithAdmin(@Param("searchText") String searchText,
			@Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT COUNT(q.answerYn) FROM WebQna q WHERE q.answerYn = :answerYn")
	int findByAnswerYn(@Param("answerYn") boolean answerYn);

	@Query(value = "SELECT t.question_type FROM (SELECT question_type, COUNT(*) c FROM web_qna GROUP BY question_type ORDER BY c DESC LIMIT 1) t", nativeQuery = true)
	QuestionType findByQuestionTypeMost();

}
