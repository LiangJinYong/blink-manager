package com.blink.domain.judge;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.enumeration.JudgeStatus;
import com.blink.web.admin.web.dto.SingleWebJudgeRecordDto;
import com.blink.web.admin.web.dto.WebJudgeDetailResponseDto;

public interface WebJudgeRepository extends JpaRepository<WebJudge, Long> {
	
	@Query("SELECT new com.blink.web.admin.web.dto.SingleWebJudgeRecordDto(j.id, j.createdAt, h.displayName, h.postcode, h.address, h.addressDetail, h.tel, j.judgeStatus) FROM WebJudge j JOIN j.hospital h WHERE h.address LIKE %:searchText% AND j.createdAt >= :time")
	Page<SingleWebJudgeRecordDto> findBySearchTextAndPeriod(@Param("searchText") String searchText, @Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT COUNT(j.id) FROM WebJudge j WHERE j.judgeStatus = :status")
	long findCountByJudgeStatus(@Param("status") JudgeStatus status);

	@Query("SELECT new com.blink.web.admin.web.dto.WebJudgeDetailResponseDto(h.name, h.displayName, j.createdAt, h.tel, h.postcode, h.address, h.addressDetail, f.fileName, f.fileKey, h.employeeName, h.employeePosition, h.employeeTel, h.employeeEmail, h.agreeSendYn, h.programInUse) FROM WebJudge j JOIN j.hospital h JOIN WebFiles f ON h.groupId = f.groupId WHERE j.id = :webJudgeId")
	Optional<WebJudgeDetailResponseDto> findJudgeDetailById(@Param("webJudgeId") Long webJudgeId);

}
