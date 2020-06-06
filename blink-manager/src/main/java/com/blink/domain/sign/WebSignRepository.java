package com.blink.domain.sign;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.domain.hospital.Hospital;
import com.blink.web.hospital.dto.webSign.WebSignResponseDto;
import com.google.common.base.Optional;

public interface WebSignRepository extends JpaRepository<WebSign, Long> {

	Long countByHospital(Hospital hospital);

	Long countByDoctorId(String doctorId);

	@Query("SELECT new com.blink.web.hospital.dto.webSign.WebSignResponseDto(s.id, s.doctorName, s.doctorPhone, s.doctorId, s.license, s.groupId, s.createdAt) FROM WebSign s WHERE s.hospital = :hospital ORDER BY createdAt DESC")
	List<WebSignResponseDto> findByHospital(@Param("hospital") Hospital hospital);

	@Modifying
	@Query("DELETE FROM WebSign s WHERE s.doctorId = :doctorId")
	void deleteByDoctorId(@Param("doctorId") String doctorId);

	Optional<WebSign> findByDoctorId(String doctorId);

}
