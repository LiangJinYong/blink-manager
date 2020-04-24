package com.blink.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserExaminationMetadataRepository extends JpaRepository<UserExaminationMetadata, Long> {
	
	@Query("SELECT u FROM UserExaminationMetadata u WHERE u.userData = :userData AND u.examinationYear = :examinationYear")
	Optional<UserExaminationMetadata> findByUserAndExaminationYear(@Param("userData") Optional<UserData> userData,
			@Param("examinationYear") Integer examinationYear);

	Optional<UserExaminationMetadata> findByUserDataAndExaminationYear(UserData userData, Integer examinationYear);

}
