package com.blink.domain.qna;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.enumeration.QuestionType;
import com.blink.web.hospital.dto.WebQnaResponseDto;

public interface WebQnaRepository extends JpaRepository<WebQna, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.WebQnaResponseDto(q.id, q.questionType, q.title, q.createdAt, q.answerYn, q.questionContent, q.answerContent, q.questionGroupId, q.answerGroupId, h.displayName, h.name, h.programInUse, h.id, h.agreeSendYn) FROM WebQna q JOIN q.hospital h WHERE title LIKE %:searchText% AND DATE(q.createdAt) BETWEEN :startDate AND :endDate AND q.hospital.id = :hospitalId")
	Page<WebQnaResponseDto> findByTitleAndPeriodWithHospital(@Param("searchText") String searchText,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("hospitalId") Long hospitalId, Pageable pageable);

	@Query("SELECT new com.blink.web.hospital.dto.WebQnaResponseDto(q.id, q.questionType, q.title, q.createdAt, q.answerYn, q.questionContent, q.answerContent, q.questionGroupId, q.answerGroupId, h.displayName, h.name, h.programInUse, h.id, h.agreeSendYn) FROM WebQna q JOIN q.hospital h WHERE title LIKE %:searchText% AND DATE(q.createdAt) BETWEEN :startDate AND :endDate")
	Page<WebQnaResponseDto> findByTitleAndPeriodWithAdmin(@Param("searchText") String searchText,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	@Query("SELECT COUNT(q.answerYn) FROM WebQna q WHERE q.answerYn = :answerYn")
	int findCountByAnswerYn(@Param("answerYn") boolean answerYn);

	@Query(value = "SELECT t.question_type FROM (SELECT question_type, COUNT(*) c FROM web_qna GROUP BY question_type ORDER BY c DESC LIMIT 1) t", nativeQuery = true)
	QuestionType findByQuestionTypeMost();

	@Modifying
	@Query("UPDATE WebQna q SET q.readAnswerYn = true WHERE q.answerYn = true AND q.hospital.id = :hospitalId")
	void updateReadAnswerStatus(@Param("hospitalId") Long hospitalId);

	@Query("SELECT COUNT(*) FROM WebQna q WHERE q.hospital.id = :hospitalId AND q.answerYn = true AND q.readAnswerYn = false")
	Integer findCountByUnread(@Param("hospitalId") Long hospitalId);

}
