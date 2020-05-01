package com.blink.domain.hospitalStatistics;

import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HospitalStatisticsRepository extends JpaRepository<HospitalStatistics, Long> {

	@Query("SELECT s FROM HospitalStatistics s JOIN s.hospital h JOIN h.admin a WHERE s.hospital.id = :hospitalId AND h.isDelete = 0 AND a.accountStatus = 1")
	Optional<HospitalStatistics> findByHospitalId(@Param("hospitalId") Long hospitalId);

	@Query(value = "SELECT t.hospital_id hospitalId, h.display_name hospitalName, t.examinationCount, t.examinationUserCount, t.agreeYCount, t.agreeNCount, h.agree_send_yn"
			+ " FROM hospital h "
			+ " INNER JOIN (SELECT hospital_id, sum(examined_count) examinationCount, sum(consent_count) examinationUserCount, sum(agreeycount) agreeYCount, sum(agreencount) agreeNCount from statistics_daily where date between  date_add(curdate(), interval -7 day) and curdate() group by hospital_id) t"
			+ " ON h.id = t.hospital_id", countQuery = "SELECT COUNT(*)" + " FROM hospital h "
					+ " INNER JOIN (SELECT hospital_id, sum(examined_count) examinationCount, sum(consent_count) examinationUserCount, sum(agreeycount) agreeYCount, sum(agreencount) agreeNCount from statistics_daily where date between  date_add(curdate(), interval -7 day) and curdate() group by hospital_id) t"
					+ " ON h.id = t.hospital_id", nativeQuery = true)
	Page<Map<String, Object>> findHospitalData(Pageable pageable);

	@Query(value = "SELECT SUM(examined_count) totalExaminationCount, SUM(consent_count) totalExaminationUserCount, sum(agreeycount) totalAgreeYCount, SUM(agreencount) totalAgreeNCount FROM statistics_daily WHERE date BETWEEN DATE_ADD(CURDATE(), INTERVAL -7 DAY) AND CURDATE()", nativeQuery = true)
	Map<String, Object> findTotalCountMap();

}
