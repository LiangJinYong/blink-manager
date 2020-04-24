package com.blink.domain.pdf;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PdfWebRepository extends JpaRepository<PdfWeb, Long> {

	@Query(value = "SELECT pw FROM PdfWeb pw WHERE pw.fileInfo.filekey LIKE CONCAT('%', :fileKey, '%')", nativeQuery = true)
	Optional<PdfWeb> findByFileKey(@Param("fileKey") String fileKey);
}
