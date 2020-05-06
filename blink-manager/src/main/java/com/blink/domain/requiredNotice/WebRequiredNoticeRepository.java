package com.blink.domain.requiredNotice;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WebRequiredNoticeRepository extends JpaRepository<WebRequiredNotice, Long> {

	@Query("SELECT n FROM WebRequiredNotice n WHERE n.id = (SELECT MAX(n2.id) FROM WebRequiredNotice n2)")
	Optional<WebRequiredNotice> findRecentRequiredNotice();
}
