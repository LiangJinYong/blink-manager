package com.blink.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
	@Query("SELECT h.id FROM UserExaminationMetadata m INNER JOIN Hospital h ON m.hospitalDataId = h.id WHERE h.displayName LIKE %:searchText% AND m.createdAt >= :searchDate GROUP BY h.id")
	Page<Long> findBusinessHospitalIds(@Param("searchText") String searchText,
			@Param("searchDate") LocalDateTime searchDate, Pageable pageable);

	@Query("SELECT COUNT(*) FROM UserExaminationMetadata m WHERE DATE(m.createdAt) = DATE(:searchDate) AND m.hospitalDataId = :hospitalId AND m.agreeYn = :agreeYn")
	Integer findAgreeYnCount(@Param("searchDate") LocalDateTime searchDate, @Param("hospitalId") Long hospitalId,
			@Param("agreeYn") Integer agreeYn);

	@Query("SELECT COUNT(*) FROM UserExaminationMetadata m WHERE DATE(m.createdAt)  = DATE(:searchDate) AND m.agreeYn = :agreeYn")
	Integer findTotalAgreeYnCount(@Param("searchDate") LocalDateTime searchLocalDate, @Param("agreeYn") Integer agreeYn);

	// 통계
	@Query("SELECT COUNT(*) FROM UserExaminationMetadata m WHERE DATE(m.createdAt) = DATE(:yesterday) AND m.hospitalDataId = :hospitalId AND m.agreeYn = :agreeYn")
	Integer findAgreeYnCount(@Param("yesterday") LocalDate yesterday, @Param("hospitalId") Long hospitalId, @Param("agreeYn") Integer agreeYn);

	@Query("SELECT COUNT(*) FROM UserExaminationMetadata m WHERE DATE(m.createdAt) = DATE(:yesterday) AND m.hospitalDataId = :hospitalId")
	Integer findExamineeCount(@Param("yesterday") LocalDate yesterday, @Param("hospitalId") Long hospitalId);
	
	@Query("SELECT m.userData.id FROM UserExaminationMetadata m WHERE DATE(m.createdAt) = DATE(:yesterday) AND m.hospitalDataId = :hospitalId")
	List<Long> findUserDataIdListByHospitalDataIdAndCreatedAt(@Param("hospitalId") Long hospitalId, @Param("yesterday") LocalDate yesterday);

}
