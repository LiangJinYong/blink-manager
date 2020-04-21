package com.blink.domain.hospital;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.hospital.HospitalDetailResponseDto;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	

	@Query("SELECT COUNT(h.id) FROM Hospital h WHERE h.employeeName = :employeeName AND h.employeeTel = :employeeTel")
	int findCountByEmployeeNameAndEmployeeTel(@Param("employeeName") String employeeName, @Param("employeeTel") String employeeTel);

	List<Hospital> findByEmployeeTel(String employeetel);

	@Query("SELECT new com.blink.web.admin.web.dto.hospital.HospitalDetailResponseDto(h.id, h.displayName, h.name, h.regDate, h.employeeName, h.tel, h.employeeTel, h.agreeSendYn, h.postcode, h.address, h.addressDetail, h.employeeEmail, h.programInUse, h.signagesStand, h.signagesMountable, h.signagesExisting, f.fileName, f.fileKey, CONCAT(f.groupId, '-', f.fileId), h.employeePosition) FROM Hospital h LEFT JOIN WebFiles f ON h.groupId = f.groupId WHERE h.displayName LIKE %:searchText% AND h.regDate >= :time")
	Page<HospitalDetailResponseDto> findBySearchTextAndPeriod(@Param("searchText") String searchText, @Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT new com.blink.web.admin.web.dto.hospital.HospitalDetailResponseDto(h.id, h.displayName, h.name, h.regDate, h.employeeName, h.tel, h.employeeTel, h.agreeSendYn, h.postcode, h.address, h.addressDetail, h.employeeEmail, h.programInUse, h.signagesStand, h.signagesMountable, h.signagesExisting, f.fileName, f.fileKey, CONCAT(f.groupId, '-', f.fileId), h.employeePosition) FROM Hospital h LEFT JOIN WebFiles f ON h.groupId = f.groupId WHERE h.id = :hospitalId")
	HospitalDetailResponseDto findDetailById(@Param("hospitalId") Long hospitalId);
}


