package com.blink.domain.examinatinPresent;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@IdClass(StatisticsDailyId.class)
@Entity
public class StatisticsDaily extends BaseTimeEntity {
	
	@Id
    private LocalDate date;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;
	
	private Long agreencount;
	private Long agreeycount;
	private Long examinedCount;
	private Long consentCount;
	private Long sendCost;
	private Long omissionCost;
	private Long paymentAmount;
	private Boolean sendAgreeYn;
	
}
