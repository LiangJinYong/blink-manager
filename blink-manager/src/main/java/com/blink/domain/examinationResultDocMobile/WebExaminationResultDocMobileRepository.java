package com.blink.domain.examinationResultDocMobile;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.examinationResultDocMobile.WebExaminationResultDocMobileResponseDto;

public interface WebExaminationResultDocMobileRepository extends JpaRepository<WebExaminationResultDocMobile, Long> {

	@Query("SELECT new com.blink.web.admin.web.dto.examinationResultDocMobile.WebExaminationResultDocMobileResponseDto(m.id, m.createdAt, u.name, u.birthDay, u.phoneNumber, m.hospitalName, m.status, m.groupId) FROM WebExaminationResultDocMobile m JOIN m.userInfo u WHERE (u.name LIKE %:searchText% OR u.birthDay LIKE %:searchText% OR u.phoneNumber LIKE %:searchText% OR m.hospitalName LIKE %:searchText%) AND DATE(m.createdAt) >= DATE(:time) ORDER BY m.createdAt DESC")
	Page<WebExaminationResultDocMobileResponseDto> findResultDocMobildList(@Param("searchText") String searchText, @Param("time") LocalDateTime time, Pageable pageable);


}
