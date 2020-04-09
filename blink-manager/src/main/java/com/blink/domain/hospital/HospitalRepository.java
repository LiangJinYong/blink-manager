package com.blink.domain.hospital;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blink.web.dto.HospitalResponseDto;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	
	@Query("SELECT new com.blink.web.dto.HospitalResponseDto(h.displayName, h.name, '2020-03-23', h.employeeName, CONCAT(h.tel, '@',h.employeeTel), h.agreenSendYn, CONCAT('(', h.postcode, ') ', h.address, ' ', h.addressDetail), h.employeeEmail) FROM Hospital h WHERE h.displayName LIKE CONCAT('%', ?1, '%')") 
//	 @Query(value="select h.id id, h.display_name displayName, h.name name, h.employee_name employeeName from hospital h where h.display_name like concat('%', ?1, '%')", nativeQuery=true)
	Page<HospitalResponseDto> findByDisplayNameContaining(String displayname, Pageable pageable);

}
