package com.blink.domain.webfiles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WebFilesRepository extends JpaRepository<WebFiles, Long> {
	@Query(value = "SELECT CONCAT(CURDATE()+'','_f', NEXTVAL(:seqName))", nativeQuery = true)
	String findFileGroupId(@Param("seqName") String seqName);
}
