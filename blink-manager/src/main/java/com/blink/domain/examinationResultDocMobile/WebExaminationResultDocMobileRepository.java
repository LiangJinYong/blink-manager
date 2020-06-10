package com.blink.domain.examinationResultDocMobile;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.examinationResultDocMobile.SingleWebExaminationResultDocMobileResponseDto;

public interface WebExaminationResultDocMobileRepository extends JpaRepository<WebExaminationResultDocMobile, Long> {

	@Query("SELECT new com.blink.web.admin.web.dto.examinationResultDocMobile.SingleWebExaminationResultDocMobileResponseDto(m.id, m.createdAt, u.name, u.birthDay, u.phoneNumber, m.hospitalName, m.hospitalAddress, m.hospitalPostcode, m.status, m.groupId, u.gender) FROM WebExaminationResultDocMobile m JOIN m.userInfo u WHERE (u.name LIKE %:searchText% OR u.birthDay LIKE %:searchText% OR u.phoneNumber LIKE %:searchText% OR m.hospitalName LIKE %:searchText%) AND DATE(m.createdAt) BETWEEN :startDate AND :endDate ORDER BY m.createdAt DESC")
	Page<SingleWebExaminationResultDocMobileResponseDto> findResultDocMobildList(@Param("searchText") String searchText, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

	@Query("SELECT COUNT(*) FROM WebExaminationResultDocMobile m")
	Integer findTotalCount();

	@Query("SELECT COUNT(*) FROM WebExaminationResultDocMobile m WHERE m.status = :status")
	Integer findCountByStatus(@Param("status") Integer status);


}
