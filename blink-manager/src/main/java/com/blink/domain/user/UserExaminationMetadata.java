package com.blink.domain.user;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.blink.domain.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class UserExaminationMetadata extends BaseTimeEntity implements Serializable {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Integer examinationYear;

	private LocalDate dateExamined;

	@JsonIgnore
	private Long hospitalDataId;

	@Transient
	private String displayName;

	private Integer agreeYn;
	private Integer agreeMail;
	private Integer agreeSms;
	private Integer agreeVisit;

	private String address;

	private Integer consentFormExistYn;
	private String specialCase;

	@JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private UserExaminationEntireDataOfOne userExaminationEntireDataOfOne;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "userExaminationMetadata")
	private List<UserExaminationMetadataDetail> userExaminationMetadataDetailList;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@JoinColumn(name = "user_data_id")
	private UserData userData;

	@Builder
	public UserExaminationMetadata(Integer agreeYn, UserData userData, Integer examinationYear, LocalDate dateExamined,
			Long hospitalDataId, String address, UserExaminationEntireDataOfOne userExaminationEntireDataOfOne,
			String specialCase, Integer agreeMail, Integer agreeSms, Integer agreeVisit) {
		this.agreeYn = agreeYn;
		this.userData = userData;
		this.examinationYear = examinationYear;
		this.dateExamined = dateExamined;
		this.hospitalDataId = hospitalDataId;
		this.address = address;
		this.userExaminationEntireDataOfOne = userExaminationEntireDataOfOne;
		this.specialCase = specialCase;
		this.agreeMail = agreeMail;
		this.agreeSms = agreeSms;
		this.agreeVisit = agreeVisit;
	}

	public void update(Integer agreeYn, LocalDate dateExamined, Long hospitalDataId, Integer agreeMail,
			Integer agreeSms, Integer agreeVisit, Integer examinationYear) {

		this.agreeYn = agreeYn;
		this.dateExamined = dateExamined;
		this.hospitalDataId = hospitalDataId;
		this.agreeMail = agreeMail;
		this.agreeSms = agreeSms;
		this.agreeVisit = agreeVisit;
		this.examinationYear = examinationYear;
	}

	public void updateAddress(String address) {
		this.address = address;
	}

	public void updateForUserExamination(LocalDate dateExamined, Integer examinationYear, Integer agreeYn,
			String specialCase, Integer agreeMail, Integer agreeSms, Integer agreeVisit) {
		this.dateExamined = dateExamined;
		this.examinationYear = examinationYear;
		this.agreeYn = agreeYn;
		this.specialCase = specialCase;
		this.agreeMail = agreeMail;
		this.agreeSms = agreeSms;
		this.agreeVisit = agreeVisit;
	}

}
