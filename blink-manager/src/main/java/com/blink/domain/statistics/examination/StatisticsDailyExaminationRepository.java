package com.blink.domain.statistics.examination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.domain.examinatinPresent.StatisticsDailyId;
import com.blink.domain.hospital.Hospital;

public interface StatisticsDailyExaminationRepository extends JpaRepository<StatisticsDailyExamination, StatisticsDailyId> {

	@Query("SELECT e FROM StatisticsDailyExamination e WHERE e.hospital.id = :hospitalId ORDER BY e.date DESC")
	Page<StatisticsDailyExamination> findByHospitalId(@Param("hospitalId") Long hospitalId, Pageable pageable);

	// 대시보드 검진종류별
	@Query("SELECT SUM(cancerBreast), SUM(cancerColon), SUM(cancerColonSecond), SUM(cancerLiver), SUM(cancerLiverSecond), SUM(cancerLug), SUM(cancerStomach), SUM(cancerWomb), SUM(firstGeneral), SUM(firstLifeChange), SUM(secondLifeHabit), SUM(outpatientFirst), SUM(outpatientCancer) FROM StatisticsDailyExamination e WHERE e.hospital = :hospital AND YEAR(e.date) = :year")
	Object[] findCountForExamination(@Param("hospital") Hospital hospital, @Param("year") Integer year);

}
