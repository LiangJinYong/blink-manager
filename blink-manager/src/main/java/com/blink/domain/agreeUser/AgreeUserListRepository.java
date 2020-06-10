package com.blink.domain.agreeUser;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.agreeUserList.AgreeUserListResponseDto;

public interface AgreeUserListRepository extends JpaRepository<AgreeUserList, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.agreeUserList.AgreeUserListResponseDto(l.id, l.createdAt, l.groupId, l.count, h.id, h.name, h.programInUse, h.agreeSendYn) FROM AgreeUserList l JOIN l.hospital h WHERE DATE(l.createdAt) BETWEEN :startDate AND :endDate AND l.hospital.id = :hospitalId")
	Page<AgreeUserListResponseDto> findBySearchTextAndPeriodWithHospital(@Param("startDate") Date startDate, @Param("endDate") Date endDate,
			@Param("hospitalId") Long hospitalId, Pageable pageable);

	@Query("SELECT new com.blink.web.hospital.dto.agreeUserList.AgreeUserListResponseDto(l.id, l.createdAt, l.groupId, l.count, h.id, h.name, h.programInUse, h.agreeSendYn) FROM AgreeUserList l JOIN l.hospital h WHERE DATE(l.createdAt) BETWEEN :startDate AND :endDate")
	Page<AgreeUserListResponseDto> findBySearchTextAndPeriodWithAdmin(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	@Query(value="SELECT SUM(count) FROM web_agree_user_list WHERE hospital_id = :hospitalId", nativeQuery = true)
	Integer findTotalCountForHospital(@Param("hospitalId") Long hospitalId);

	@Query(value="SELECT SUM(count) FROM web_agree_user_list", nativeQuery = true)
	Integer findTotalCountForAdmin();

}