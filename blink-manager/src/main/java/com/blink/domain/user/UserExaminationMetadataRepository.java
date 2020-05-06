package com.blink.domain.user;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.business.SingleBusinessResponseDto;
import com.blink.web.hospital.dto.userExamination.SingleUserExaminationResponseDto;

public interface UserExaminationMetadataRepository extends JpaRepository<UserExaminationMetadata, Long> {

	@Query("SELECT u FROM UserExaminationMetadata u WHERE u.userData = :userData AND u.examinationYear = :examinationYear")
	Optional<UserExaminationMetadata> findByUserAndExaminationYear(@Param("userData") Optional<UserData> userData,
			@Param("examinationYear") Integer examinationYear);

	Optional<UserExaminationMetadata> findByUserDataAndExaminationYear(UserData userData, Integer examinationYear);

	// 수검자 관리
	@Query("SELECT new com.blink.web.hospital.dto.userExamination.SingleUserExaminationResponseDto(m.id, u.name, u.gender, u.birthday, u.phone, '', m.dateExamined, m.agreeYn, m.consentFormExistYn, m.address, m.specialCase, u.ssnPartial, m.agreeMail, m.agreeSms, m.agreeVisit, h.id, h.name) FROM UserExaminationMetadata m JOIN Hospital h ON m.hospitalDataId = h.id JOIN m.userData u WHERE m.hospitalDataId = :hospitalId AND u.name LIKE %:searchText% AND m.createdAt > :time")
	Page<SingleUserExaminationResponseDto> findBySearchTextAndPeriodWithHospital(@Param("hospitalId") Long hospitalId,
			@Param("searchText") String searchText, @Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT new com.blink.web.hospital.dto.userExamination.SingleUserExaminationResponseDto(m.id, u.name, u.gender, u.birthday, u.phone, h.displayName, m.dateExamined, m.agreeYn, m.consentFormExistYn, m.address, m.specialCase, u.ssnPartial, m.agreeMail, m.agreeSms, m.agreeVisit, h.id, h.name) FROM UserExaminationMetadata m JOIN m.userData u JOIN Hospital h ON m.hospitalDataId = h.id WHERE u.name LIKE %:searchText% AND m.createdAt > :time")
	Page<SingleUserExaminationResponseDto> findBySearchTextAndPeriodWithHospitalWithAdmin(
			@Param("searchText") String searchText, @Param("time") LocalDateTime time, Pageable pageable);

	@Query("SELECT COUNT(m.id) FROM UserExaminationMetadata m WHERE m.hospitalDataId = :hospitalDataId")
	Integer findUserExaminationCountForHospital(@Param("hospitalDataId") Long hospitalId);

	@Query("SELECT COUNT(m.id) FROM UserExaminationMetadata m WHERE m.hospitalDataId = :hospitalId AND (m.consentFormExistYn = 0 OR consentFormExistYn IS NULL)")
	Integer findNonexistConsetFormCountForHospital(@Param("hospitalId") Long hospitalId);

	@Query("SELECT COUNT(m.id) FROM UserExaminationMetadata m")
	Integer findUserExaminationCountForAdmin();

	@Query("SELECT COUNT(m.id) FROM UserExaminationMetadata m WHERE m.consentFormExistYn = 0 OR consentFormExistYn IS NULL")
	Integer findNonexistConsetFormCountForAdmin();

	// 업무관리
	@Query(value = "SELECT h.id FROM user_examination_metadata m INNER JOIN hospital h ON m.hospital_data_id = h.id where h.display_name LIKE %:searchText% AND DATE(m.created_at) = :searchDate GROUP BY h.id", nativeQuery = true)
	Page<BigInteger> findBusinessHospitalIds(@Param("searchText") String searchText,
			@Param("searchDate") String searchDate, Pageable pageable);

	@Query(value = "SELECT COUNT(*) FROM user_examination_metadata WHERE DATE(created_at)  = :searchDate AND hospital_data_id = :hospitalId AND agree_yn = :agreeYn", nativeQuery = true)
	Integer findAgreeYnCount(@Param("searchDate") String searchDate, @Param("hospitalId") Long hospitalId,
			@Param("agreeYn") Integer agreeYn);

}
