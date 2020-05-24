package com.blink.domain.statistics.ageGender;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blink.domain.examinatinPresent.StatisticsDailyId;

public interface StatisticsDailyAgeGenderRepository extends JpaRepository<StatisticsDailyAgeGender, StatisticsDailyId> {

}
