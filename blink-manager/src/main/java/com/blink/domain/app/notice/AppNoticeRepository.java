package com.blink.domain.app.notice;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppNoticeRepository extends JpaRepository<AppNotice, Long> {

	@Query("SELECT n FROM AppNotice n WHERE title LIKE %:title% AND createdAt >= :time")
	Page<AppNotice> findByNameAndPeriod(@Param("title") String title, @Param("time") LocalDateTime time, Pageable pageable);

}
