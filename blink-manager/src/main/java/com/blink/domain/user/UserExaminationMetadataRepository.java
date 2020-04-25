package com.blink.domain.user;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.userExamination.SingleUserExaminationResponseDto;

public interface UserExaminationMetadataRepository extends JpaRepository<UserExaminationMetadata, Long> {
	
	@Query("SELECT u FROM UserExaminationMetadata u WHERE u.userData = :userData AND u.examinationYear = :examinationYear")
	Optional<UserExaminationMetadata> findByUserAndExaminationYear(@Param("userData") Optional<UserData> userData,
			@Param("examinationYear") Integer examinationYear);

	Optional<UserExaminationMetadata> findByUserDataAndExaminationYear(UserData userData, Integer examinationYear);

	// 수검자 관리
	@Query("SELECT new com.blink.web.hospital.dto.userExamination.SingleUserExaminationResponseDto(m.id, u.name, u.gender, u.birthday, u.phone, m.dateExamined, m.agreeYn, m.consentFormExistYn, m.address, m.specialCase) FROM UserExaminationMetadata m JOIN m.userData u WHERE m.hospitalDataId = :hospitalId AND u.name LIKE %:searchText% AND m.createdAt > :time")
	Page<SingleUserExaminationResponseDto> findBySearchTextAndPeriodWithHospital(@Param("hospitalId") Long hospitalId, @Param("searchText") String searchText,
			@Param("time") LocalDateTime time, Pageable pageable);

}
