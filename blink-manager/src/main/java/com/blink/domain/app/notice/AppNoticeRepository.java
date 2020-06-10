package com.blink.domain.app.notice;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppNoticeRepository extends JpaRepository<AppNotice, Long> {

	@Query("SELECT n FROM AppNotice n WHERE title LIKE %:searchText% AND DATE(createdAt) BETWEEN :startDate AND :endDate")
	Page<AppNotice> findByNameAndPeriod(@Param("searchText") String searchText, @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

}
