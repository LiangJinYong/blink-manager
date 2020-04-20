package com.blink.domain.hospital;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.HospitalResponseDto;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	
	@Query("SELECT new com.blink.web.admin.web.dto.HospitalResponseDto(h.displayName, h.name, '2020-03-23', h.employeeName, CONCAT(h.tel, '@',h.employeeTel), h.agreeSendYn, CONCAT('(', h.postcode, ') ', h.address, ' ', h.addressDetail), h.employeeEmail) FROM Hospital h WHERE h.displayName LIKE CONCAT('%', ?1, '%')") 
	Page<HospitalResponseDto> findByDisplayNameContaining(String displayname, Pageable pageable);

	@Query("SELECT COUNT(h.id) FROM Hospital h WHERE h.employeeName = :employeeName AND h.employeeTel = :employeeTel")
	int findCountByEmployeeNameAndEmployeeTel(@Param("employeeName") String employeeName, @Param("employeeTel") String employeeTel);

	
	List<Hospital> findByEmployeeTel(String employeetel);
}
