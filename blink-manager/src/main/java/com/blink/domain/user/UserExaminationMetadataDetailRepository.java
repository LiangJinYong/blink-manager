package com.blink.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.userExamination.InspectionTypeDto;

public interface UserExaminationMetadataDetailRepository extends JpaRepository<UserExaminationMetadataDetail, Long> {
	List<UserExaminationMetadataDetail> findAllByUserExaminationMetadataId(long userExaminationMetadataId, Sort sort);

	@Query(value="SELECT d.* FROM user_examination_metadata_detail d WHERE d.user_examination_metadata_id = :metadataId AND EXTRACT(YEAR FROM date_examined) = :examinationYear AND inspection_type = :inspectionType AND inspection_sub_type = :inspectionSubType", nativeQuery=true)
	Optional<UserExaminationMetadataDetail> findByMetaDataAndExaminationYearAndType(@Param("metadataId") Long metadataId, @Param("examinationYear") Integer examinationYear, @Param("inspectionType") Integer inspectionType, @Param("inspectionSubType") Integer inspectionSubType);

	// 수검자 관리
	@Query("SELECT new com.blink.web.hospital.dto.userExamination.InspectionTypeDto(d.inspectionType, d.inspectionSubType) FROM UserExaminationMetadataDetail d WHERE d.userExaminationMetadata.id = :userExaminationId")
	List<InspectionTypeDto> findInspectionTypeByMetadata(@Param("userExaminationId") Long userExaminationId);

	
}
