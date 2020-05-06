package com.blink.domain.examinatinPresent;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsMonthlyRepository extends JpaRepository<StatisticsMonthly, StatisticsMonthlyId> {
	@Query(value = "select sum(s.examinedCount) as cnt, avg(s.examinedCount) as average, sum(s.agreeYCount) as y, sum(s.agreeNCount) as n from StatisticsMonthly s where s.hospital.id = :hospitalId")
    Optional<Tuple> findDashboardResultByHospital(Long hospitalId);

    /**
     * 기간 내 동의여부 통계
     * @param from
     * @param to
     * @return
     */
    @Query(value = "select sum(tb.cnt) cnt, avg(tb.cnt) average, sum(tb.y) y, sum(tb.n) n from (select sum(s.examined_count) as cnt, avg(s.examined_count) as average, sum(s.agreeYCount) as y, sum(s.agreeNCount) as n from statistics_monthly s where s.created_at between :from and :to) as tb", nativeQuery = true)
    Optional<Tuple> findDashboardResult(ZonedDateTime from, ZonedDateTime to);


    /**
     * 전체 동의여부 통계
     * @return
     */
    @Query(value = "select sum(tb.cnt) cnt, avg(tb.cnt) average, sum(tb.y) y, sum(tb.n) n from (select sum(s.examined_count) as cnt, avg(s.examined_count) as average, sum(s.agreeYCount) as y, sum(s.agreeNCount) as n from statistics_monthly s ) as tb", nativeQuery = true)
    Optional<Tuple> findDashboardResult();

    /**
     * 병원별 동의여부 통계
     * @return
     */
    @Query(value = "select sum(tb.cnt) cnt, avg(tb.cnt) average, sum(tb.y) y, sum(tb.n) n, hospital_id from (select sum(s.examined_count) as cnt, avg(s.examined_count) as average, sum(s.agreeYCount) as y, sum(s.agreeNCount) as n, hospital_id from statistics_monthly s group by hospital_id) as tb group by hospital_id", nativeQuery = true)
    Optional<Tuple> findDashboardHospitalsResult();

    /**
     * 병원별 동의여부 통계
     * @return
     */
    @Query(value = "select sum(tb.cnt) cnt, avg(tb.cnt) average, sum(tb.y) y, sum(tb.n) n, hospital_id from (select sum(s.examined_count) as cnt, avg(s.examined_count) as average, sum(s.agreeYCount) as y, sum(s.agreeNCount) as n, hospital_id from statistics_monthly s where date between :from and :to group by hospital_id) as tb group by hospital_id", nativeQuery = true)
    List<Tuple> findDashboardHospitalsResult(LocalDate from, LocalDate to);


    /**
     * 병원별  통계
     * @param from
     * @param to
     * @return
     */

    @Query(value = "select s from StatisticsMonthly s where hospital_id = :hospitalId and date between :from and :to order by date asc")
    List <StatisticsMonthly> findStatisticsByHospital(Long hospitalId, LocalDate from, LocalDate to);

    /**
     * 전체 병원 통계
     * @param from
     * @param to
     * @return
     */
    @Query(value = "select s.date, sum(s.agreeYCount) as y, sum(s.agreeNCount) as n, sum(s.examinedCount) as cnt from StatisticsDaily s where date between :from and :to group by date order by date asc")
    List <Tuple> findStatistics(LocalDate from, LocalDate to);
}
