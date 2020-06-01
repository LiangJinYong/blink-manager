package com.blink.domain.statistics.ageGender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.domain.examinatinPresent.StatisticsDailyId;
import com.blink.domain.hospital.Hospital;

public interface StatisticsDailyAgeGenderRepository extends JpaRepository<StatisticsDailyAgeGender, StatisticsDailyId> {

	@Query("SELECT SUM(male20Count) AS male20Count, SUM(male30Count) AS male30Count, SUM(male40Count) AS male40Count, SUM(male50Count) AS male50Count, SUM(male60Count) AS male60Count, SUM(male70Count) AS male70Count, SUM(male80Count) AS male80Count, SUM(female20Count) AS female20Count, SUM(female30Count) AS female30Count, SUM(female40Count) AS female40Count, SUM(female50Count) AS female50Count, SUM(female60Count) AS female60Count, SUM(female70Count) AS female70Count, SUM(female80Count) AS female80Count FROM StatisticsDailyAgeGender g WHERE YEAR(g.date) = :year AND g.hospital = :hospital")
	Object[] findCountForAgeAndGender(@Param("hospital") Hospital hospital, @Param("year") Integer year);

//	@Query("SELECT g FROM StatisticsDailyAgeGender g WHERE g.hospital.id = :hospitalId ORDER BY g.date DESC")
//	Page<StatisticsDailyAgeGender> findByHospitalId(@Param("hospitalId") Long hospitalId, Pageable pageable);

}
