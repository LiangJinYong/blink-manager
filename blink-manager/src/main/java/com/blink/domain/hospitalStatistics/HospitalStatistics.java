package com.blink.domain.hospitalStatistics;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "statistics_hospital")
public class HospitalStatistics extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long examinedCount;

	private Long examinedUserCount;

	private Long agreeyCount;

	private Long agreenCount;

	private Long sendCount;

	private Long sendCost;

	@Column(name = "age_20")
	private Long age20;

	@Column(name = "age_30")
	private Long age30;

	@Column(name = "age_40")
	private Long age40;

	@Column(name = "age_50")
	private Long age50;

	@Column(name = "age_60")
	private Long age60;

	@Column(name = "age_70")
	private Long age70;

	@Column(name = "age_80")
	private Long age80;

	private Long genderMale;

	private Long genderFemale;

	private Long examinationFirst;

	private Long examinationSecond;

	private Long examinationCancerTotal;

	private Long examinationCancerWomb;

	private Long examinationCancerStomach;

	private Long examinationCancerColon;

	private Long examinationCancerColonSecond;

	private Long examinationCancerBreast;

	private Long examinationCancerLiver;

	private Long examinationCancerLiverSecond;

	private Long examinationCancerLug;
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@Builder
	public HospitalStatistics(Hospital hospital) {
		this.hospital = hospital;
	}
}
