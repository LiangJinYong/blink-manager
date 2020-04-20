package com.blink.domain.agreeUser;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.agreeUserList.AgreeUserListResponseDto;

public interface AgreeUserListRepository extends JpaRepository<AgreeUserList, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.agreeUserList.AgreeUserListResponseDto(l.id, l.createdAt, l.groupId) FROM AgreeUserList l WHERE l.createdAt >= :time AND l.hospital.id = :hospitalId")
	Page<AgreeUserListResponseDto> findBySearchTextAndPeriodWithHospital(@Param("time") LocalDateTime time,
			@Param("hospitalId") Long hospitalId, Pageable pageable);

	@Query("SELECT new com.blink.web.hospital.dto.agreeUserList.AgreeUserListResponseDto(l.id, l.createdAt, l.groupId) FROM AgreeUserList l WHERE l.createdAt >= :time")
	Page<AgreeUserListResponseDto> findBySearchTextAndPeriodWithAdmin(@Param("time")LocalDateTime time, Pageable pageable);

}