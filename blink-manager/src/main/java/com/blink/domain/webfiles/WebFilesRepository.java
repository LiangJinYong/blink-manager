package com.blink.domain.webfiles;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.WebFileResponseDto;

public interface WebFilesRepository extends JpaRepository<WebFiles, Long> {
	@Query(value = "SELECT CONCAT(CURDATE()+'','_f', NEXTVAL(:seqName))", nativeQuery = true)
	String findFileGroupId(@Param("seqName") String seqName);

	@Query("SELECT new com.blink.web.admin.web.dto.WebFileResponseDto(wf.fileKey, wf.fileName, CONCAT(wf.groupId, '-', wf.fileId)) FROM WebFiles wf WHERE groupId = :groupId")
	List<WebFileResponseDto> findByGroupId(@Param("groupId") String groupId);

	Optional<WebFiles> findByGroupIdAndFileId(String groupId, Integer fileId);

	@Query("SELECT MAX(wf.fileId) FROM WebFiles wf where wf.groupId = :groupId")
	int findMaxFileIdByGroupId(@Param("groupId") String groupId);

	@Modifying
	@Query(value="DELETE FROM web_files WHERE group_id = :groupId", nativeQuery=true)
	void deleteByGroupId(@Param("groupId") String groupId);
}
