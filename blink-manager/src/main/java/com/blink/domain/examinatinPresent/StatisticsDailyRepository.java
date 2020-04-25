package com.blink.domain.examinatinPresent;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blink.web.hospital.dto.examinatinPresent.ExaminatinPresentHospitalResponseDto;

public interface StatisticsDailyRepository extends JpaRepository<StatisticsDaily, Long> {

	@Query("SELECT new com.blink.web.hospital.dto.examinatinPresent.ExaminatinPresentHospitalResponseDto(d.date, d.examinedCount, d.agreeycount, d.agreencount, d.consentCount, d.sendCost, d.omissionCost, d.paymentAmount, d.sendAgreeYn) FROM StatisticsDaily d WHERE d.hospital.id = :hospitalId AND d.createdAt > :time")
	Page<ExaminatinPresentHospitalResponseDto> findBySearchTextAndPeriod(@Param("hospitalId") Long hospitalId, @Param("time") LocalDateTime time, Pageable pageable);

}

