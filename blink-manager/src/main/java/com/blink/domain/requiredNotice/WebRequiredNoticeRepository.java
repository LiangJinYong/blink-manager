package com.blink.domain.requiredNotice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WebRequiredNoticeRepository extends JpaRepository<WebRequiredNotice, Long> {

	@Query("select n from WebRequiredNotice n where n.version = (select max(n2.version) from WebRequiredNotice n2)")
	Optional<WebRequiredNotice> findRecentRequiredNotice();
}
