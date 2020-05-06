package com.blink.domain.consentForm;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;
import com.blink.enumeration.ReceiveType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class WebConsentForm extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate receiveDate;

	@Enumerated(EnumType.STRING)
	private ReceiveType receiveType;

	private String receiveTypeText;

	private String consentYear;

	private String consentMonth;

	private Long count;

	private String groupId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@Builder
	public WebConsentForm(Hospital hospital, LocalDate receiveDate, ReceiveType receiveType, String receiveTypeText, String consentYear,
			String consentMonth, String groupId, Long count) {
		this.hospital = hospital;
		this.receiveDate = receiveDate;
		this.receiveType = receiveType;
		this.receiveTypeText = receiveTypeText;
		this.consentYear = consentYear;
		this.consentMonth = consentMonth;
		this.groupId = groupId;
		this.count = count;
	}

	/*
	@Builder(builderMethodName = "hospitalBuilder")
	public WebConsentForm(Hospital hospital, String consentYear, String consentMonth, String groupId) {
		this.hospital = hospital;
		this.consentYear = consentYear;
		this.consentMonth = consentMonth;
		this.groupId = groupId;
	}
	*/
}