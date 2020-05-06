package com.blink.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blink.domain.StatisticsInterface;
import com.blink.domain.examinatinPresent.StatisticsDailyRepository;
import com.blink.domain.examinatinPresent.StatisticsMonthlyRepository;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.hospital.HospitalRepository;
import com.blink.domain.user.DataTablePdfWebRepository;
import com.blink.domain.user.DataTableUserExaminationMetadataRepository;

import lombok.AllArgsConstructor;
import lombok.Data;

@Service
public class StatisticsService {
	 @Data
	    @AllArgsConstructor
	    public static class AgreementStatisticsResult implements Serializable {
	        private LocalDate date;
	        private Long examinedCount;
	        private Long agreeYCount;
	        private Long agreeNCount;
	    }

	    @Data
	    @AllArgsConstructor
	    public static class TotalResult implements Serializable {
	        private Long hospitalId;
	        private Long count;
	    }

	    @Data
	    @AllArgsConstructor
	    public static class HospitalDashboardResult implements Serializable {
	        private Long hospitalId;
	        private Long totalCount;
	        private Double monthlyAvgCount;
	        private Long agreeYCount;
	        private Long agreeNCount;
	    }

	    @Data
	    @AllArgsConstructor
	    public static class AdminDashboardResult implements Serializable {
	        //전체수
	        private Long totalCount;

	        //완료수
	        private Long completeCount;

	        //대기수
	        private Long waitingCount;
	    }

	    @Data
	    @AllArgsConstructor
	    public static class AdminDashboardResultWithHospital implements Serializable {
	        private Long hospitalId;

	        //전체수
	        private Long totalCount;

	        //완료수
	        private Long completeCount;

	        //대기수
	        private Long waitingCount;

	        private Long agreeYCount;
	        private Long agreeNCount;
	    }

	    @Data
	    @AllArgsConstructor
	    public static class PieChartResult implements Serializable {
	        private String group;
	        private Long count;
	    }

	    private Function<StatisticsInterface, AgreementStatisticsResult> statisticsInterfaceAgreementStatisticsResultFunction = (statisticsInterface) -> {
	        LocalDate date = statisticsInterface.getLocalDate();
	        Long examinedCount = statisticsInterface.getExaminedCount();
	        Long agreeYCount = statisticsInterface.getAgreeYCount();
	        Long agreeNCount = statisticsInterface.getAgreeNCount();
	        return new AgreementStatisticsResult(date, examinedCount, agreeYCount, agreeNCount);
	    } ;

	    private Function<Tuple, AgreementStatisticsResult> adminTupleAgreementStatisticsResultFunction = (tuple) -> {
	        LocalDate date = ((java.sql.Date)tuple.get("date")).toLocalDate();
	        Long totalCount = (Long)tuple.get("cnt");
	        Long agreeYCount = (Long)tuple.get("y");
	        Long agreeNCount = (Long)tuple.get("n");
	        return new AgreementStatisticsResult(date, totalCount, agreeYCount, agreeNCount);
	    } ;

	    private Function<Tuple, AdminDashboardResult> adminTupleAdminDashboardResultFunction = (tuple) -> {
	        //Long totalCount = (Long)tuple.get("cnt");
	        Long totalCount = ((BigInteger)tuple.get("cnt")).longValue();
	        Long completeCount = ((BigDecimal)tuple.get("done")).longValue();
	        Long waitingCount = ((BigDecimal)tuple.get("wait")).longValue();
	        return new AdminDashboardResult(totalCount, completeCount, waitingCount);
	    } ;

	    private Function<Tuple, AdminDashboardResultWithHospital> adminTupleAdminDashboardHospitalResultFunction = (tuple) -> {
	        //Long totalCount = (Long)tuple.get("cnt");
	        Long hospitalId = ((BigInteger) tuple.get("hospital_id")).longValue();
	        Long totalCount = ((BigInteger)tuple.get("cnt")).longValue();
	        Long completeCount = ((BigDecimal)tuple.get("done")).longValue();
	        Long waitingCount = ((BigDecimal)tuple.get("wait")).longValue();
	        return new AdminDashboardResultWithHospital(hospitalId, totalCount, completeCount, waitingCount, 0L, 0L);
	    } ;


	    private Function<Tuple, AgreementStatisticsResult> adminStatisticsInterfaceAgreementStatisticsResultFunction = (tuple) -> {
	        LocalDate date = ((java.sql.Date)tuple.get("date")).toLocalDate();
	        Long totalCount = (Long)tuple.get("cnt");
	        Long agreeYCount = (Long)tuple.get("y");
	        Long agreeNCount = (Long)tuple.get("n");
	        return new AgreementStatisticsResult(date, totalCount, agreeYCount, agreeNCount);
	    } ;

	    private Function<Tuple, HospitalDashboardResult> tupleHospitalDashboardResultFunction = (tuple) -> {
	        Long totalCount = (Long)tuple.get("cnt");
	        Double monthlyAvgCount = (Double)tuple.get("average");
	        Long agreeYCount = (Long)tuple.get("y");
	        Long agreeNCount = (Long)tuple.get("n");
	        return new HospitalDashboardResult(0L, totalCount, monthlyAvgCount, agreeYCount, agreeNCount);
	    } ;

	    private Function<Tuple, HospitalDashboardResult> adminTupleHospitalDashboardResultFunction = (tuple) -> {
	        Long totalCount = ((BigDecimal)tuple.get("cnt")).longValue();
	        Double monthlyAvgCount = ((BigDecimal)tuple.get("average")).doubleValue();
	        Long agreeYCount = ((BigDecimal)tuple.get("y")).longValue();
	        Long agreeNCount = ((BigDecimal)tuple.get("n")).longValue();
	        return new HospitalDashboardResult(0L, totalCount, monthlyAvgCount, agreeYCount, agreeNCount);
	    } ;

	    private Function<Tuple, HospitalDashboardResult> adminTupleHospitalDashboardResultListFunction = (tuple) -> {
	        Long hospitalId = ((BigInteger)tuple.get("hospital_id")).longValue();
	        Long totalCount = ((BigDecimal)tuple.get("cnt")).longValue();
	        Double monthlyAvgCount = ((BigDecimal)tuple.get("average")).doubleValue();
	        Long agreeYCount = ((BigDecimal)tuple.get("y")).longValue();
	        Long agreeNCount = ((BigDecimal)tuple.get("n")).longValue();
	        return new HospitalDashboardResult(hospitalId, totalCount, monthlyAvgCount, agreeYCount, agreeNCount);
	    } ;

	    private Function<Tuple, PieChartResult> tupleHospitalPieChartResultListFunction = (tuple) -> {
	        String group = (String)tuple.get("group");
	        Long totalCount = ((BigInteger)tuple.get("cnt")).longValue();
	        return new PieChartResult(group, totalCount);
	    } ;




	    @Autowired
	    private HospitalRepository hospitalRepository;

	    @Autowired
	    private StatisticsDailyRepository statisticsDailyRepository;

	    @Autowired
	    private StatisticsMonthlyRepository statisticsMonthlyRepository;

	    @Autowired
	    private DataTablePdfWebRepository dataTablePdfWebRepository;

	    @Autowired
	    private DataTableUserExaminationMetadataRepository dataTableUserExaminationMetadataRepository;

	    /**
	     * 병원별 대시보드 통계 조회 (병원PDF 전체처리수, 병원PDF 월평균처리수, 동의수, 비동의수)
	     * @return
	     */
	    public HospitalDashboardResult findHospitalDashboardResult(String hospitalName) {
	        Hospital hospital = hospitalRepository.findByName(hospitalName);
	        return statisticsMonthlyRepository.findDashboardResultByHospital(hospital.getId()).map(tupleHospitalDashboardResultFunction).orElse(new HospitalDashboardResult(0L, 0L, 0.0, 0L, 0L));
	    }

	    /**
	     * 기간내 대시보드 통계 조회 (병원PDF 전체처리수, 병원PDF 월평균처리수, 동의수, 비동의수)
	     * @return
	     */
	    public HospitalDashboardResult findHospitalDashboardResult(ZonedDateTime from, ZonedDateTime to) {
	        return statisticsMonthlyRepository.findDashboardResult(from, to).map(adminTupleHospitalDashboardResultFunction).orElse(new HospitalDashboardResult(0L, 0L, 0.0, 0L, 0L));
	    }

	    /**
	     * 대시보드 통계 조회 (병원PDF 전체처리수, 병원PDF 월평균처리수, 동의수, 비동의수)
	     * @return
	     */
	    public HospitalDashboardResult findHospitalDashboardResult() {
	        return statisticsMonthlyRepository.findDashboardResult().map(adminTupleHospitalDashboardResultFunction).orElse(new HospitalDashboardResult(0L, 0L, 0.0, 0L, 0L));
	    }

	    /**
	     * 대시보드 연령별 통계 차트
	     *
	     * @param hospitalName
	     * @return
	     */
	    public List<PieChartResult> findAgeGroupChartResult(String hospitalName) {
	        Hospital hospital = hospitalRepository.findByName(hospitalName);
	        return dataTableUserExaminationMetadataRepository.findAgeGroupChartResult(hospital.getId()).stream().map(tupleHospitalPieChartResultListFunction).collect(Collectors.toList());
	    }

	    /**
	     * 대시보드 성별 통계 차트
	     *
	     * @param hospitalName
	     * @return
	     */
	    public List<PieChartResult> findGenderGroupChartResult(String hospitalName) {
	        Hospital hospital = hospitalRepository.findByName(hospitalName);
	        return dataTableUserExaminationMetadataRepository.findGenderGroupChartResult(hospital.getId()).stream().map(tupleHospitalPieChartResultListFunction).collect(Collectors.toList());
	    }

	    /**
	     * 대시보드 검진 타입별 통계 차트
	     *
	     * @param hospitalName
	     * @return
	     */
	    public List<PieChartResult> findInspectionTypeGroupChartResult(String hospitalName) {
	        Hospital hospital = hospitalRepository.findByName(hospitalName);
	        return dataTableUserExaminationMetadataRepository.findInspectionTypeGroupChartResult(hospital.getId()).stream().map(tupleHospitalPieChartResultListFunction).collect(Collectors.toList());
	    }

	    /**
	     * 어드민 대시보드 조회 - 전체 동의수 조회
	     * @return
	     */
	    public AdminDashboardResult findAdminDashboardResult(ZonedDateTime from, ZonedDateTime to) {
	        return dataTablePdfWebRepository.findStatisticsAdminDashboardResult(from, to).map(adminTupleAdminDashboardResultFunction).orElse(new AdminDashboardResult(0L, 0L, 0L));
	    }

	    /**
	     * 어드민 대시보드 결과 - 병원별 동의수 조회
	     * @return
	     */
	    public List<AdminDashboardResultWithHospital> findAdminDashboardHospitalResult(ZonedDateTime from, ZonedDateTime to) {
	        Map<Long, HospitalDashboardResult> map = statisticsMonthlyRepository.findDashboardHospitalsResult(from.toLocalDate(), to.toLocalDate()).stream().map(adminTupleHospitalDashboardResultListFunction).collect(Collectors.toMap(l -> l.hospitalId, l->l));
	        return dataTablePdfWebRepository.findStatisticsAdminDashboardHospitalResult(from, to).stream().map(l -> {
	            AdminDashboardResultWithHospital adminDashboardResultWithHospital =  adminTupleAdminDashboardHospitalResultFunction.apply(l);
	            HospitalDashboardResult hospitalDashboardResult = map.getOrDefault(adminDashboardResultWithHospital.getHospitalId(), new HospitalDashboardResult(0L, 0L, 0.0, 0L, 0L));

	            adminDashboardResultWithHospital.setAgreeYCount(hospitalDashboardResult.getAgreeYCount());
	            adminDashboardResultWithHospital.setAgreeNCount(hospitalDashboardResult.getAgreeNCount());

	            return adminDashboardResultWithHospital;
	        }).collect(Collectors.toList());
	    }

	    /**
	     * 병원별 최근 1년간 동의 여부 통계 조회
	     * @param hospitalName
	     * @return
	     */
	    public List<AgreementStatisticsResult> findStatisticsMonthlyListRecentlyOneYear(String hospitalName) {
	        Hospital hospital = hospitalRepository.findByName(hospitalName);
	        ZonedDateTime toTarget = ZonedDateTime.now();//.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
	        ZonedDateTime fromTarget = toTarget.minusMonths(12);

	        ZonedDateTime to = toTarget.withDayOfMonth(toTarget.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59).withNano(0);
	        ZonedDateTime from = fromTarget.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
	        return statisticsMonthlyRepository.findStatisticsByHospital(hospital.getId(), from.toLocalDate(), to.toLocalDate()).stream().map(statisticsInterfaceAgreementStatisticsResultFunction).collect(Collectors.toList());
	    }

	    /**
	     * 전체 병원 최근 1년간 동의 여부 통계 조회
	     * @return
	     */
	    public List<AgreementStatisticsResult> findStatisticsMonthlyListRecentlyOneYear() {
	        ZonedDateTime toTarget = ZonedDateTime.now();//.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
	        ZonedDateTime fromTarget = toTarget.minusMonths(12);

	        ZonedDateTime to = toTarget.withDayOfMonth(toTarget.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59).withNano(0);
	        ZonedDateTime from = fromTarget.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
	        return statisticsMonthlyRepository.findStatistics(from.toLocalDate(), to.toLocalDate()).stream().map(adminStatisticsInterfaceAgreementStatisticsResultFunction).collect(Collectors.toList());
	    }

	    /**
	     * 최근 1주간 병원별 일별 통계 조회
	     * @param hospitalName
	     * @return
	     */
	    public List<AgreementStatisticsResult> findStatisticsDailyList(String hospitalName) {
	        Hospital hospital = hospitalRepository.findByName(hospitalName);

	        ZonedDateTime toTarget = ZonedDateTime.now();//.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
	        ZonedDateTime fromTarget = toTarget.minusWeeks(1);

	        ZonedDateTime to = toTarget.withDayOfMonth(toTarget.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59).withNano(0);
	        ZonedDateTime from = fromTarget.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
	        return statisticsDailyRepository.findStatisticsByHospital(hospital.getId(), from.toLocalDate(), to.toLocalDate()).stream().map(statisticsInterfaceAgreementStatisticsResultFunction).collect(Collectors.toList());
	    }

	    /**
	     * 최근 1주간 전체병원 일별 통계 조회
	     * @return
	     */

	    public List<AgreementStatisticsResult> findStatisticsDailyList() {
	        ZonedDateTime toTarget = ZonedDateTime.now();//.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
	        ZonedDateTime fromTarget = toTarget.minusWeeks(1);

	        ZonedDateTime to = toTarget.withDayOfMonth(toTarget.getMonth().maxLength()).withHour(23).withMinute(59).withSecond(59).withNano(0);
	        ZonedDateTime from = fromTarget.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
	        return statisticsDailyRepository.findStatistics(from.toLocalDate(), to.toLocalDate()).stream().map(adminTupleAgreementStatisticsResultFunction).collect(Collectors.toList());
	    }
}
