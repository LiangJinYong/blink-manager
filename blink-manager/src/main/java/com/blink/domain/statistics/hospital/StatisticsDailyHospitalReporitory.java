package com.blink.domain.statistics.hospital;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.domain.examinatinPresent.StatisticsDailyId;

public interface StatisticsDailyHospitalReporitory extends JpaRepository<StatisticsDailyHospital, StatisticsDailyId> {

}
