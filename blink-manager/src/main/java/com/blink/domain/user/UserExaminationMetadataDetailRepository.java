package com.blink.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.enumeration.InspectionSubType;
import com.blink.enumeration.InspectionType;
import com.blink.web.admin.app.dto.userExamination.MobileResultDocMetadataDetailResponseDto;
import com.blink.web.hospital.dto.userExamination.InspectionTypeDto;

public interface UserExaminationMetadataDetailRepository extends JpaRepository<UserExaminationMetadataDetail, Long> {
	List<UserExaminationMetadataDetail> findAllByUserExaminationMetadataId(long userExaminationMetadataId, Sort sort);

	@Query(value = "SELECT d.* FROM user_examination_metadata_detail d WHERE d.user_examination_metadata_id = :metadataId AND EXTRACT(YEAR FROM date_examined) = :examinationYear AND inspection_type = :inspectionType AND inspection_sub_type = :inspectionSubType", nativeQuery = true)
	List<UserExaminationMetadataDetail> findByMetaDataAndExaminationYearAndType(
			@Param("metadataId") Long metadataId, @Param("examinationYear") Integer examinationYear,
			@Param("inspectionType") Integer inspectionType, @Param("inspectionSubType") Integer inspectionSubType);

	// 수검자 관리
	@Query("SELECT new com.blink.web.hospital.dto.userExamination.InspectionTypeDto(d.inspectionType, d.inspectionSubType, w.fileInfo.filekey, w.fileInfo.filename, d.dateExamined) FROM UserExaminationMetadataDetail d LEFT JOIN d.pdfIndividualWeb w WHERE d.userExaminationMetadata.id = :userExaminationId")
	List<InspectionTypeDto> findInspectionTypeByMetadata(@Param("userExaminationId") Long userExaminationId);

	@Query("SELECT COUNT(d.id) FROM UserExaminationMetadataDetail d")
	Integer findDiagnoseCount();

	@Query(value = "SELECT h.display_name FROM hospital h WHERE id = " + "(SELECT t.hospital_data_id FROM ("
			+ "SELECT m.hospital_data_id, COUNT(*) c FROM user_examination_metadata_detail d INNER JOIN user_examination_metadata m ON d.user_examination_metadata_id = m.id GROUP BY m.hospital_data_id ORDER BY C DESC LIMIT 1) t)", nativeQuery = true)
	String findMostExaminationHospitalName();

	@Query("SELECT COUNT(d.id) FROM UserExaminationMetadataDetail d JOIN d.userExaminationMetadata m WHERE m.hospitalDataId = :hospitalId AND d.inspectionType = :type AND DATE(m.createdAt) >= DATE(:time)")
	Integer findExaminationCountByInspectionForHospital(@Param("hospitalId") Long hospitalId, @Param("type") InspectionType type,  @Param("time") LocalDateTime time);

	@Query("SELECT COUNT(d.id) FROM UserExaminationMetadataDetail d JOIN d.userExaminationMetadata m  WHERE d.inspectionType = :type AND DATE(m.createdAt) > DATE(:time)")
	Integer findExaminationCountByInspectionForAdmin(@Param("time") LocalDateTime time, @Param("type") InspectionType type);

	// 통계
	@Query("SELECT COUNT(*) FROM UserExaminationMetadataDetail d JOIN d.userExaminationMetadata m WHERE DATE(m.createdAt) = DATE(:yesterday) AND m.hospitalDataId = :hospitalId")
	Integer findExaminationCount(@Param("yesterday") LocalDate yesterday, @Param("hospitalId") Long hospitalId);

	@Query("SELECT COUNT(*) FROM UserExaminationMetadataDetail d JOIN d.userExaminationMetadata m WHERE DATE(m.createdAt) = DATE(:yesterday) AND m.hospitalDataId = :hospitalId AND d.inspectionType = :inspectionType AND d.inspectionSubType = :inspectionSubType")
	Integer findExaminationCount(@Param("yesterday") LocalDate yesterday, @Param("hospitalId") Long hospitalId, @Param("inspectionType") InspectionType inspectionType,
			@Param("inspectionSubType") InspectionSubType inspectionSubType);

	// 병원별 대시보디 연도 전체 검진수
	@Query("SELECT COUNT(*) FROM UserExaminationMetadataDetail d JOIN d.userExaminationMetadata m WHERE m.hospitalDataId = :hospitalId AND m.examinationYear = :year")
	Integer findYearlyTotalExaminationCount(@Param("hospitalId") Long hospitalId, @Param("year") Integer year);

	// parser json
	@Query("SELECT d FROM UserExaminationMetadataDetail d WHERE d.userExaminationMetadata = :metadata AND d.inspectionType = :inspectionType AND d.inspectionSubType = :inspectionSubType")
	List<UserExaminationMetadataDetail> findByMetaDataAndInspectionType(@Param("metadata") UserExaminationMetadata metadata,
			@Param("inspectionType") InspectionType inspectionType, @Param("inspectionSubType") InspectionSubType inspectionSubType);

	// mobile get examination result doc
	@Query("SELECT new com.blink.web.admin.app.dto.userExamination.MobileResultDocMetadataDetailResponseDto(d.inspectionType, d.inspectionSubType, d.dateExamined, d.doctorName, d.doctorLicenseNumber, d.inspectionPlace, d.dateInterpreted) FROM UserExaminationMetadataDetail d WHERE d.userExaminationMetadata = :userExaminationMetadata")
	List<MobileResultDocMetadataDetailResponseDto> findMobileMetadataDetail(@Param("userExaminationMetadata")
			UserExaminationMetadata userExaminationMetadata);

}
