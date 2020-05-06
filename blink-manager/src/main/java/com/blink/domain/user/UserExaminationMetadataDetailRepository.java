package com.blink.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.enumeration.InspectionType;
import com.blink.web.hospital.dto.userExamination.InspectionTypeDto;

public interface UserExaminationMetadataDetailRepository extends JpaRepository<UserExaminationMetadataDetail, Long> {
	List<UserExaminationMetadataDetail> findAllByUserExaminationMetadataId(long userExaminationMetadataId, Sort sort);

	@Query(value = "SELECT d.* FROM user_examination_metadata_detail d WHERE d.user_examination_metadata_id = :metadataId AND EXTRACT(YEAR FROM date_examined) = :examinationYear AND inspection_type = :inspectionType AND inspection_sub_type = :inspectionSubType", nativeQuery = true)
	Optional<UserExaminationMetadataDetail> findByMetaDataAndExaminationYearAndType(
			@Param("metadataId") Long metadataId, @Param("examinationYear") Integer examinationYear,
			@Param("inspectionType") Integer inspectionType, @Param("inspectionSubType") Integer inspectionSubType);

	// 수검자 관리
	@Query("SELECT new com.blink.web.hospital.dto.userExamination.InspectionTypeDto(d.inspectionType, d.inspectionSubType, w.fileInfo.filekey) FROM UserExaminationMetadataDetail d JOIN d.pdfIndividualWeb w WHERE d.userExaminationMetadata.id = :userExaminationId")
	List<InspectionTypeDto> findInspectionTypeByMetadata(@Param("userExaminationId") Long userExaminationId);

	@Query("SELECT COUNT(d.id) FROM UserExaminationMetadataDetail d")
	Integer findDiagnoseCount();

	@Query(value = "SELECT h.display_name FROM hospital h WHERE id = " + "(SELECT t.hospital_data_id FROM ("
			+ "SELECT m.hospital_data_id, COUNT(*) c FROM user_examination_metadata_detail d INNER JOIN user_examination_metadata m ON d.user_examination_metadata_id = m.id GROUP BY m.hospital_data_id ORDER BY C DESC LIMIT 1) t)", nativeQuery = true)
	String findMostExaminationHospitalName();

	@Query("SELECT COUNT(d.id) FROM UserExaminationMetadataDetail d JOIN d.userExaminationMetadata m WHERE m.hospitalDataId = :hospitalId AND d.inspectionType = :type")
	Integer findExaminationCountByInspectionForHospital(@Param("hospitalId") Long hospitalId, @Param("type") InspectionType type);

	@Query("SELECT COUNT(d.id) FROM UserExaminationMetadataDetail d WHERE d.inspectionType = :type")
	Integer findExaminationCountByInspectionForAdmin(@Param("type") InspectionType type);

}
