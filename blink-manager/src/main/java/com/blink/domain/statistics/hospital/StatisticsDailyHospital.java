package com.blink.domain.statistics.hospital;

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
@Table(name = "web_statistics_daily_hospital")
public class StatisticsDailyHospital extends BaseTimeEntity {
	@Id
	private LocalDate date;

	@Id
	@JoinColumn(name = "hospital_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Hospital hospital;
	
	
	private Integer year;
	private Integer month;
	private Integer dayOfWeek;

	@Column(name = "agreencount", columnDefinition = "bigint(20)")
	private Integer agreeNCount;

	@Column(name = "agreeycount", columnDefinition = "bigint(20)")
	private Integer agreeYCount;

	@Column(columnDefinition = "bigint(20)")
	private Integer examinationCount;

	@Column(columnDefinition = "bigint(20)")
	private Integer examineeCount;

	@Column(columnDefinition = "bigint(20)")
	private Integer consentCount;

	@Column(columnDefinition = "bigint(20)")
	private Integer sentCount;

	@Column(columnDefinition = "bigint(20)")
	private Integer sentCost;

	@Column(columnDefinition = "bigint(20)")
	private Integer omissionCost;

	@Column(columnDefinition = "bigint(20)")
	private Integer agreeSendYn;

	@Column(columnDefinition = "bigint(20)")
	private Integer paymentAmount;

	@Builder
	public StatisticsDailyHospital(LocalDate date, Hospital hospital, Integer year, Integer month, Integer dayOfWeek, Integer agreeNCount, Integer agreeYCount,
			Integer examinationCount, Integer examineeCount, Integer consentCount, Integer sentCount, Integer sentCost,
			Integer omissionCost, Integer agreeSendYn, Integer paymentAmount) {
		this.date = date;
		this.hospital = hospital;
		this.year = year;
		this.month = month;
		this.dayOfWeek = dayOfWeek;
		this.agreeNCount = agreeNCount;
		this.agreeYCount = agreeYCount;
		this.examinationCount = examinationCount;
		this.examineeCount = examineeCount;
		this.consentCount = consentCount;
		this.sentCount = sentCount;
		this.sentCost = sentCost;
		this.omissionCost = omissionCost;
		this.agreeSendYn = agreeSendYn;
		this.paymentAmount = paymentAmount;
	}
}
