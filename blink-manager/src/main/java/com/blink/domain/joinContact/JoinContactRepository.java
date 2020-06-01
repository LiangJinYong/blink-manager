package com.blink.domain.joinContact;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JoinContactRepository extends JpaRepository<JoinContact, Long> {

	@Query("SELECT new com.blink.web.admin.web.dto.joinConcact.SingleJoinConcactResponseDto(c.id, c.clinicName, c.regDate, c.name, c.tel, c.answerYn, c.inquiry, c.usedProgram, c.email, c.answerContent, c.visitReserveYn, c.visitDate, c.visitAim) FROM JoinContact c WHERE (c.clinicName LIKE %:searchText% OR c.usedProgram LIKE %:searchText% OR c.tel LIKE %:searchText%) AND c.regDate > :time")
	Page<JoinContact> findBySearchTextAndPeriod(@Param("searchText") String searchText, @Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT COUNT(c.id) FROM JoinContact c WHERE c.answerYn = :answerYn")
	Long findCountByAnswerYn(@Param("answerYn") Boolean answerYn);

	@Query(value="SELECT IFNULL(t.used_program, '') FROM " + 
			"(SELECT used_program, COUNT(*) c FROM joincontact WHERE used_program IS NOT NULL GROUP BY used_program ORDER BY c desc LIMIT 1) t", nativeQuery=true)
	String findMostUserProgram();

}
