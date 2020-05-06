package com.blink.domain.hospital;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.hospital.HospitalDetailResponseDto;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
	
	public List<Hospital> findAllByRoleId(long roleId);
	public List<Hospital> findAllByRoleIdAndIsDeleteEquals(long roleId, boolean isDelete);
	public Hospital findByName(String name);
    public Optional<Hospital> findOptionalByName(String name);
    public int countByRoleIdAndIsDeleteEquals(long roleId, boolean isDelete);

    @Query("select h.id from Hospital h where h.createdAt <= :dateTime")
    public List<Long> findHospitalIdsByRegDateBefore(ZonedDateTime dateTime);

    @Query("select h.id from Hospital h ")
    public List<Long> findHospitalIds();
    
    // added
	@Query("SELECT COUNT(h.id) FROM Hospital h WHERE h.employeeName = :employeeName AND h.employeeTel = :employeeTel")
	int findCountByEmployeeNameAndEmployeeTel(@Param("employeeName") String employeeName, @Param("employeeTel") String employeeTel);

	List<Hospital> findByEmployeeTel(String employeetel);

	@Query("SELECT new com.blink.web.admin.web.dto.hospital.HospitalDetailResponseDto(h.id, h.displayName, h.name, h.regDate, h.employeeName, h.tel, h.employeeTel, h.agreeSendYn, h.postcode, h.address, h.addressDetail, h.employeeEmail, h.programInUse, h.signagesStand, h.signagesMountable, h.signagesExisting, f.fileName, f.fileKey, CONCAT(f.groupId, '-', f.fileId), h.employeePosition) FROM Hospital h LEFT JOIN WebFiles f ON h.groupId = f.groupId WHERE h.displayName LIKE %:searchText% AND h.regDate >= :time")
	Page<HospitalDetailResponseDto> findBySearchTextAndPeriod(@Param("searchText") String searchText, @Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT new com.blink.web.admin.web.dto.hospital.HospitalDetailResponseDto(h.id, h.displayName, h.name, h.regDate, h.employeeName, h.tel, h.employeeTel, h.agreeSendYn, h.postcode, h.address, h.addressDetail, h.employeeEmail, h.programInUse, h.signagesStand, h.signagesMountable, h.signagesExisting, f.fileName, f.fileKey, CONCAT(f.groupId, '-', f.fileId), h.employeePosition) FROM Hospital h LEFT JOIN WebFiles f ON h.groupId = f.groupId WHERE h.id = :hospitalId")
	HospitalDetailResponseDto findDetailById(@Param("hospitalId") Long hospitalId);
	
	@Query("SELECT COUNT(h.id) FROM Hospital h WHERE h.isDelete = 0")
	public Integer findHospitalCount();

}
