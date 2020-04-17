package com.blink.domain.webfiles;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.WebFileResponseDto;

public interface WebFilesRepository extends JpaRepository<WebFiles, Long> {
	@Query(value = "SELECT CONCAT(CURDATE()+'','_f', NEXTVAL(:seqName))", nativeQuery = true)
	String findFileGroupId(@Param("seqName") String seqName);

	@Query("SELECT new com.blink.web.admin.web.dto.WebFileResponseDto(wf.fileKey, wf.fileName) FROM WebFiles wf WHERE groupId = :groupId")
	List<WebFileResponseDto> findByGroupId(@Param("groupId") String groupId);
}
