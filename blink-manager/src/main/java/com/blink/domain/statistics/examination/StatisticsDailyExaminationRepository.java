package com.blink.domain.statistics.examination;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.domain.examinatinPresent.StatisticsDailyId;

public interface StatisticsDailyExaminationRepository extends JpaRepository<StatisticsDailyExamination, StatisticsDailyId> {

}
