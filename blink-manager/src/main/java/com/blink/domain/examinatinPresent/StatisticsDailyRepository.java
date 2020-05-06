package com.blink.domain.examinatinPresent;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.examinatinPresent.ExaminatinPresentHospitalResponseDto;

public interface StatisticsDailyRepository extends JpaRepository<StatisticsDaily, StatisticsDailyId> {

	@Query("SELECT new com.blink.web.hospital.dto.examinatinPresent.ExaminatinPresentHospitalResponseDto(d.date, d.examinedCount, d.agreeYCount, d.agreeNCount) FROM StatisticsDaily d WHERE d.hospital.id = :hospitalId AND d.createdAt > :time")
	Page<ExaminatinPresentHospitalResponseDto> findBySearchTextAndPeriod(@Param("hospitalId") Long hospitalId,
			@Param("time") ZonedDateTime zonedTime, Pageable pageable);

	@Query(value = "SELECT concat(EXTRACT(YEAR FROM date), '-' ,lpad(EXTRACT(MONTH FROM date), '2', 0)) as dt , sum(agreencount) n , sum(agreeycount) y FROM statistics_daily where date between :from and :to GROUP BY EXTRACT(YEAR_MONTH FROM date)", nativeQuery = true)
	List<Tuple> findGroupByMonth(ZonedDateTime from, ZonedDateTime to);

	@Query(value = "SELECT concat(EXTRACT(YEAR FROM date), '-' ,lpad(EXTRACT(MONTH FROM date), '2', 0), '-' ,lpad(EXTRACT(DAY FROM date), '2', 0)) as dt, sum(agreencount) n , sum(agreeycount) y FROM statistics_daily where date between :from and :to", nativeQuery = true)
	List<Tuple> findGroupByDay(ZonedDateTime from, ZonedDateTime to);

	@Query(value = "select sum(examined_count) cnt, hospital_id from statistics_daily where date = :dt  group by hospital_id", nativeQuery = true)
	List<Tuple> findExaminedDateCountWithHospital(LocalDate dt);

	@Query(value = "SELECT concat(EXTRACT(YEAR FROM date), '-' ,lpad(EXTRACT(MONTH FROM date), '2', 0)) as dt , sum(agreencount) n , sum(agreeycount) y, sum(examined_count) cnt , hospital_id FROM statistics_daily where date between :from and :to GROUP BY EXTRACT(YEAR_MONTH FROM date), hospital_id", nativeQuery = true)
	List<Tuple> findStatisticsMonthlyWithHospital(ZonedDateTime from, ZonedDateTime to);

	/**
	 * 병원별 통계
	 * 
	 * @param from
	 * @param to
	 * @return
	 */

	@Query(value = "select s from StatisticsDaily s where hospital_id = :hospitalId and date between :from and :to order by date asc")
	List<StatisticsDaily> findStatisticsByHospital(Long hospitalId, LocalDate from, LocalDate to);

	/**
	 * 전체 병원 통계
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	@Query(value = "select s.date, sum(s.examinedCount) as cnt, sum(s.agreeYCount) as y, sum(s.agreeNCount) as n from StatisticsDaily s where date between :from and :to group by date order by date asc")
	List<Tuple> findStatistics(LocalDate from, LocalDate to);
}
