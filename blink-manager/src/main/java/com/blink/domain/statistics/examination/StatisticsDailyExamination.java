package com.blink.domain.statistics.examination;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.examinatinPresent.StatisticsDailyId;
import com.blink.domain.hospital.Hospital;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@IdClass(StatisticsDailyId.class)
@Table(name = "web_statistics_daily_examination")
public class StatisticsDailyExamination extends BaseTimeEntity {

	@Id
	private LocalDate date;

	@Id
	@JoinColumn(name = "hospital_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Hospital hospital;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer cancerBreast;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer cancerColon;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer cancerColonSecond;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer cancerLiver;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer cancerLiverSecond;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer cancerLug;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer cancerStomach;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer cancerWomb;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer firstGeneral;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer firstLifeChange;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer secondLifeHabit;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer outpatientFirst;

	@Column(columnDefinition = "BIGINT(20) DEFAULT 0")
	private Integer outpatientCancer;

	@Builder
	public StatisticsDailyExamination(LocalDate date, Hospital hospital, Integer cancerBreast, Integer cancerColon,
			Integer cancerColonSecond, Integer cancerLiver, Integer cancerLiverSecond, Integer cancerLug,
			Integer cancerStomach, Integer cancerWomb, Integer firstGeneral, Integer firstLifeChange,
			Integer secondLifeHabit, Integer outpatientFirst, Integer outpatientCancer) {

		this.date = date;
		this.hospital = hospital;
		this.cancerBreast = cancerBreast;
		this.cancerColon = cancerColon;
		this.cancerColonSecond = cancerColonSecond;
		this.cancerLiver = cancerLiver;
		this.cancerLiverSecond = cancerLiverSecond;
		this.cancerLug = cancerLug;
		this.cancerStomach = cancerStomach;
		this.cancerWomb = cancerWomb;
		this.firstGeneral = firstGeneral;
		this.firstLifeChange = firstLifeChange;
		this.secondLifeHabit = secondLifeHabit;
		this.outpatientFirst = outpatientFirst;
		this.outpatientCancer = outpatientCancer;
	}

}
