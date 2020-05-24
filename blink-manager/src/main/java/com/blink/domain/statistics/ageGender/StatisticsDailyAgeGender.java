package com.blink.domain.statistics.ageGender;

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

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@IdClass(StatisticsDailyId.class)
@Table(name = "web_statistics_daily_age")
public class StatisticsDailyAgeGender extends BaseTimeEntity {

	@Id
	private LocalDate date;

	@Id
	@JoinColumn(name = "hospital_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Hospital hospital;

	@Column(name = "m_age_20")
	private Integer male20Count;
	@Column(name = "m_age_30")
	private Integer male30Count;
	@Column(name = "m_age_40")
	private Integer male40Count;
	@Column(name = "m_age_50")
	private Integer male50Count;
	@Column(name = "m_age_60")
	private Integer male60Count;
	@Column(name = "m_age_70")
	private Integer male70Count;
	@Column(name = "m_age_80")
	private Integer male80Count;

	@Column(name = "f_age_20")
	private Integer female20Count;
	@Column(name = "f_age_30")
	private Integer female30Count;
	@Column(name = "f_age_40")
	private Integer female40Count;
	@Column(name = "f_age_50")
	private Integer female50Count;
	@Column(name = "f_age_60")
	private Integer female60Count;
	@Column(name = "f_age_70")
	private Integer female70Count;
	@Column(name = "f_age_80")
	private Integer female80Count;

	@Builder
	public StatisticsDailyAgeGender(LocalDate date, Hospital hospital, Integer male20Count, Integer male30Count,
			Integer male40Count, Integer male50Count, Integer male60Count, Integer male70Count, Integer male80Count,
			Integer female20Count, Integer female30Count, Integer female40Count, Integer female50Count,
			Integer female60Count, Integer female70Count, Integer female80Count) {
		this.date = date;
		this.hospital = hospital;
		
		this.male20Count = male20Count;
		this.male30Count = male30Count;
		this.male40Count = male40Count;
		this.male50Count = male50Count;
		this.male60Count = male60Count;
		this.male70Count = male70Count;
		this.male80Count = male80Count;

		this.female20Count = female20Count;
		this.female30Count = female30Count;
		this.female40Count = female40Count;
		this.female50Count = female50Count;
		this.female60Count = female60Count;
		this.female70Count = female70Count;
		this.female80Count = female80Count;
	}
}
