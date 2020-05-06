package com.blink.jobs;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Tuple;
import javax.transaction.Transactional;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.blink.domain.examinatinPresent.StatisticsDaily;
import com.blink.domain.examinatinPresent.StatisticsDailyId;
import com.blink.domain.examinatinPresent.StatisticsDailyRepository;
import com.blink.domain.examinatinPresent.StatisticsMonthly;
import com.blink.domain.examinatinPresent.StatisticsMonthlyId;
import com.blink.domain.examinatinPresent.StatisticsMonthlyRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.user.DataTableUserExaminationMetadataRepository;
import com.blink.service.StatisticsService;

import lombok.extern.slf4j.Slf4j;

/**
 * 통계 업데이트 로직
 */
@Slf4j
public class SchedulerJob implements Job {

	private static class NotFoundJobException extends Exception {
		private NotFoundJobException(String message) {
			super(message);
		}
	}

	@Autowired
	private HospitalRepository hospitalRepository;

	@Autowired
	private DataTableUserExaminationMetadataRepository dataTableUserExaminationMetadataRepository;

	@Autowired
	private StatisticsDailyRepository statisticsDailyRepository;

	@Autowired
	private StatisticsMonthlyRepository statisticsMonthlyRepository;

	/**
	 * 전일 기준 통계 작성을 위해 한 시간 전 기준으로 동작한다.
	 * 
	 * @param optionalDate
	 *            날짜 옵션
	 */
	@Transactional
	public void updateDailyStatistics(Optional<LocalDate> optionalDate) {

		// 서버 시간(database)은 UTC 기준이므로 한국 시간 기준으로 동작하기위해 시간 계산 +09:00
		ZonedDateTime targetDate = optionalDate
				.map(date -> ZonedDateTime.of(date, LocalTime.now(), ZoneId.of("UTC"))
						.withZoneSameInstant(ZoneId.of("Asia/Seoul")))
				.orElseGet(() -> ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Seoul")).minusHours(1));

		ZonedDateTime to = targetDate.withHour(23).withMinute(59).withSecond(59).withNano(999999999);
		ZonedDateTime from = targetDate.withHour(0).withMinute(0).withSecond(0).withNano(0);

		Set<Long> hospitalIds = new HashSet<>(hospitalRepository.findHospitalIds());

		LocalDate korDate = targetDate.toLocalDate();

		// 검진 수 카운트
		Map<Long, Long> examinedCountMap = dataTableUserExaminationMetadataRepository
				.findExaminedDateCountWithHospital(from, to).stream().map(tuple -> {
					Long hospitalDataId = ((BigInteger) tuple.get("hospital_data_id")).longValue();
					Long count = ((BigInteger) tuple.get("cnt")).longValue();
					return new StatisticsService.TotalResult(hospitalDataId, count);
				}).collect(Collectors.toMap(StatisticsService.TotalResult::getHospitalId,
						StatisticsService.TotalResult::getCount));

		log.info("updateDailyStatistics from : {}, to :{} / target : {}, now : {}", from, to, targetDate,
				ZonedDateTime.now());
		for (Tuple tuple : dataTableUserExaminationMetadataRepository.findStatisticsDailyWithHospital(from, to)) {
			// LocalDate dt = ((java.sql.Date) tuple.get("dt")).toLocalDate();
			Long hospitalDataId = ((BigInteger) tuple.get("hospital_data_id")).longValue();
			long agreeYCount = ((BigDecimal) tuple.get("y")).longValue();
			long agreeNCount = ((BigDecimal) tuple.get("n")).longValue();
			long examinedCount = examinedCountMap.getOrDefault(hospitalDataId, 0L);

			log.info("daily update date : {}, hospitalDateId : {}, y : {}, n : {}, cnt : {}", korDate, hospitalDataId,
					agreeYCount, agreeNCount, examinedCount);
			Optional<StatisticsDaily> optionalDailyStatistics = statisticsDailyRepository
					.findById(new StatisticsDailyId(korDate, hospitalDataId));
			if (optionalDailyStatistics.isPresent()) {
				StatisticsDaily statisticsDaily = optionalDailyStatistics.get();
				statisticsDaily.setAgreeYCount(agreeYCount);
				statisticsDaily.setAgreeNCount(agreeNCount);
				statisticsDaily.setExaminedCount(examinedCount);
				statisticsDailyRepository.save(statisticsDaily);
			} else {
				Hospital hospital = hospitalRepository.getOne(hospitalDataId);
				statisticsDailyRepository
						.save(new StatisticsDaily(korDate, hospital, examinedCount, agreeYCount, agreeNCount));
			}

			hospitalIds.remove(hospitalDataId);
			examinedCountMap.remove(hospitalDataId);
		}

		// 통계없는 병원 생성
		for (Long hospitalId : hospitalIds) {
			Optional<StatisticsDaily> optionalStatisticsMonthly = statisticsDailyRepository
					.findById(new StatisticsDailyId(korDate, hospitalId));
			Long examinedCount = examinedCountMap.getOrDefault(hospitalId, 0L);
			if (optionalStatisticsMonthly.isPresent()) {
				StatisticsDaily statisticsDaily = optionalStatisticsMonthly.get();
				statisticsDaily.setExaminedCount(examinedCount);
				statisticsDailyRepository.save(statisticsDaily);
			} else {
				Hospital hospital = hospitalRepository.getOne(hospitalId);
				statisticsDailyRepository.save(new StatisticsDaily(korDate, hospital, examinedCount, 0L, 0L));
			}
			examinedCountMap.remove(hospitalId);
		}

		// 검진 통계 없는 병원 업데이트
		for (Map.Entry<Long, Long> entry : examinedCountMap.entrySet()) {
			long hospitalId = entry.getKey();
			Optional<StatisticsDaily> optionalStatisticsDaily = statisticsDailyRepository
					.findById(new StatisticsDailyId(korDate, hospitalId));
			Long examinedCount = entry.getValue();
			if (optionalStatisticsDaily.isPresent()) {
				StatisticsDaily statisticsDaily = optionalStatisticsDaily.get();
				statisticsDaily.setExaminedCount(examinedCount);
				statisticsDailyRepository.save(statisticsDaily);
			} else {
				Hospital hospital = hospitalRepository.getOne(hospitalId);
				statisticsDailyRepository.save(new StatisticsDaily(korDate, hospital, examinedCount, 0L, 0L));
			}
		}
	}

	/**
	 * 하루전 날짜 기준으로 동작한다
	 * 
	 * @param optionalDate
	 *            날짜 옵션
	 */
	@Transactional
	public void updateMonthlyStatistics(Optional<LocalDate> optionalDate) {

		ZonedDateTime targetDate = optionalDate
				.map(date -> ZonedDateTime.of(date, LocalTime.now(), ZoneId.of("UTC"))
						.withZoneSameInstant(ZoneId.of("Asia/Seoul")))
				.orElseGet(() -> ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Seoul")).minusDays(1));
		ZonedDateTime to = targetDate.withDayOfMonth(targetDate.getMonth().maxLength()).withHour(23).withMinute(59)
				.withSecond(59).withNano(999999999);
		ZonedDateTime from = targetDate.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

		Set<Long> hospitalIds = new HashSet<>(hospitalRepository.findHospitalIdsByRegDateBefore(to));
		LocalDate korDate = from.toLocalDate();

		log.info("updateMonthlyStatistics from : {}, to :{} / target : {}, now : {}", from, to, targetDate,
				ZonedDateTime.now());
		for (Tuple tuple : statisticsDailyRepository.findStatisticsMonthlyWithHospital(from, to)) {
			Long hospitalDataId = ((BigInteger) tuple.get("hospital_id")).longValue();
			long examinedCount = ((BigDecimal) tuple.get("cnt")).longValue();
			long agreeYCount = ((BigDecimal) tuple.get("y")).longValue();
			long agreeNCount = ((BigDecimal) tuple.get("n")).longValue();

			log.info("monthly update date : {}, hospitalDateId : {}, y : {}, n : {}, cnt : {}", korDate, hospitalDataId,
					agreeYCount, agreeNCount, examinedCount);
			Optional<StatisticsMonthly> optionalDailyStatistics = statisticsMonthlyRepository
					.findById(new StatisticsMonthlyId(korDate, hospitalDataId));
			if (optionalDailyStatistics.isPresent()) {
				StatisticsMonthly statisticsMonthly = optionalDailyStatistics.get();
				statisticsMonthly.setAgreeYCount(agreeYCount);
				statisticsMonthly.setAgreeNCount(agreeNCount);
				statisticsMonthly.setExaminedCount(examinedCount);
				statisticsMonthlyRepository.save(statisticsMonthly);
			} else {
				Hospital hospital = hospitalRepository.getOne(hospitalDataId);
				statisticsMonthlyRepository
						.save(new StatisticsMonthly(korDate, hospital, examinedCount, agreeYCount, agreeNCount));
			}

			hospitalIds.remove(hospitalDataId);
		}

		// 통계없는 병원 생성
		for (Long hospitalId : hospitalIds) {
			Optional<StatisticsMonthly> optionalStatisticsMonthly = statisticsMonthlyRepository
					.findById(new StatisticsMonthlyId(korDate, hospitalId));
			if (optionalStatisticsMonthly.isPresent()) {

			} else {
				Hospital hospital = hospitalRepository.getOne(hospitalId);
				statisticsMonthlyRepository.save(new StatisticsMonthly(korDate, hospital, 0L, 0L, 0L));
			}
		}
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Job Executing trigger ( key : {} ), job ( key : {}, description : {} ) : {}",
				context.getTrigger().getKey(), context.getJobDetail().getKey(), context.getJobDetail().getDescription(),
				LocalDateTime.now().toString());

		try {
			updateDailyStatistics(Optional.empty());
			updateMonthlyStatistics(Optional.empty());
		} catch (Exception e) {
			log.error("SchedulerJob", e);
		}
	}
}
