package com.blink.domain.notice;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.admin.web.dto.WebNoticeResponseDto;

public interface WebNoticeRepository extends JpaRepository<WebNotice, Long> {
	
	@Query("SELECT new com.blink.web.admin.web.dto.WebNoticeResponseDto(n.id, n.title, n.description, n.createdAt, n.groupId) FROM WebNotice n WHERE title LIKE %:searchText% AND createdAt >= :time")
	Page<WebNoticeResponseDto> findByTitleAndPeriod(@Param("searchText") String searchText, @Param("time") LocalDateTime time, Pageable pageable);
	
}
