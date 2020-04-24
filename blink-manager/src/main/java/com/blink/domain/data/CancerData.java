package com.blink.domain.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.blink.domain.user.UserExaminationEntireDataOfOne;
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
public class CancerData implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@OneToOne(mappedBy = "cancerData", fetch = FetchType.LAZY)
	private UserExaminationEntireDataOfOne userExaminationEntireDataOfOne;

	// 자궁암
	@Column(columnDefinition = "varchar(255) default null comment '자궁경부암 검사항목'")
	private String cancerWombInspectionItems;

	@Column(columnDefinition = "varchar(255) default null comment '자궁경부암 검진 결과'")
	private String cancerWombResult;

	@Column(columnDefinition = "varchar(255) default null comment '자궁경부암 검진 판정'")
	private String cancerWombDecision;

	@Column(columnDefinition = "text default null comment '자궁경부암 검진 권고사항'")
	private String cancerWombRecommendedText;

	// 간암
	@Column(columnDefinition = "varchar(255) default null comment '간암 검사항목'")
	private String cancerLiverInspectionItems;

	@Column(columnDefinition = "varchar(255) default null comment '간암 검진 결과'")
	private String cancerLiverResult;

	@Column(columnDefinition = "varchar(255) default null comment '간암 검진 판정'")
	private String cancerLiverDecision;

	@Column(columnDefinition = "text default null comment '간암 검진 권고사항'")
	private String cancerLiverRecommendedText;

	// 위암
	@Column(columnDefinition = "varchar(255) default null comment '위암 검사항목'")
	private String cancerStomachInspectionItems;

	@Column(columnDefinition = "varchar(255) default null comment '위암 검진 결과'")
	private String cancerStomachResult;

	@Column(columnDefinition = "varchar(255) default null comment '위암 검진 판정'")
	private String cancerStomachDecision;

	@Column(columnDefinition = "text default null comment '위암 검진 권고사항'")
	private String cancerStomachRecommendedText;

	// 대장암
	@Column(columnDefinition = "varchar(255) default null comment '대장암 검사항목'")
	private String cancerColonInspectionItems;

	@Column(columnDefinition = "varchar(255) default null comment '대장암 검진 결과'")
	private String cancerColonResult;

	@Column(columnDefinition = "varchar(255) default null comment '대장암 검진 판정'")
	private String cancerColonDecision;

	@Column(columnDefinition = "text default null comment '대장암 검진 권고사항'")
	private String cancerColonRecommendedText;

	// 유방암
	@Column(columnDefinition = "varchar(255) default null comment '유방암 검사항목'")
	private String cancerBreastInspectionItems;

	@Column(columnDefinition = "varchar(255) default null comment '유방암 검진 결과'")
	private String cancerBreastResult;

	@Column(columnDefinition = "varchar(255) default null comment '유방암 검진 판정'")
	private String cancerBreastDecision;

	@Column(columnDefinition = "text default null comment '유방암 검진 권고사항'")
	private String cancerBreastRecommendedText;

	// 간암 하반기
	@Column(columnDefinition = "varchar(255) default null comment '간암 하반기 검사항목'")
	private String cancerLiverSecondInspectionItems;

	@Column(columnDefinition = "varchar(255) default null comment '간암 하반기 검진 결과'")
	private String cancerLiverSecondResult;

	@Column(columnDefinition = "varchar(255) default null comment '간암 하반기 검진 판정'")
	private String cancerLiverSecondDecision;

	@Column(columnDefinition = "text default null comment '간암 하반기 검진 권고사항'")
	private String cancerLiverSecondRecommendedText;

	// 폐암
	@Column(columnDefinition = "varchar(255) default null comment '폐암 검사항목'")
	private String cancerLungInspectionItems;

	@Column(columnDefinition = "varchar(255) default null comment '폐암 검진 결과'")
	private String cancerLungResult;

	@Column(columnDefinition = "varchar(255) default null comment '폐암 검진 판정'")
	private String cancerLungDecision;

	@Column(columnDefinition = "text default null comment '폐암 검진 권고사항'")
	private String cancerLungRecommendedText;

	// 대장암 하반기
	@Column(columnDefinition = "varchar(255) default null comment '대장암 하반기 검사항목'")
	private String cancerColonSecondInspectionItems;

	@Column(columnDefinition = "varchar(255) default null comment '대장암 하반기 검진 결과'")
	private String cancerColonSecondResult;

	@Column(columnDefinition = "varchar(255) default null comment '대장암 하반기 검진 판정'")
	private String cancerColonSecondDecision;

	@Column(columnDefinition = "text default null comment '대장암 하반기 검진 권고사항'")
	private String cancerColonSecondRecommendedText;

	@Builder
	public CancerData(String cancerWombInspectionItems, String cancerWombResult, String cancerWombDecision,
			String cancerWombRecommendedText, String cancerLiverInspectionItems, String cancerLiverResult,
			String cancerLiverDecision, String cancerLiverRecommendedText, String cancerStomachInspectionItems,
			String cancerStomachResult, String cancerStomachDecision, String cancerStomachRecommendedText,
			String cancerColonInspectionItems, String cancerColonResult, String cancerColonDecision,
			String cancerColonRecommendedText, String cancerBreastInspectionItems, String cancerBreastResult,
			String cancerBreastDecision, String cancerBreastRecommendedText, String cancerLiverSecondInspectionItems,
			String cancerLiverSecondResult, String cancerLiverSecondDecision, String cancerLiverSecondRecommendedText,
			String cancerLungInspectionItems, String cancerLungResult, String cancerLungDecision,
			String cancerLungRecommendedText, String cancerColonSecondInspectionItems, String cancerColonSecondResult,
			String cancerColonSecondDecision, String cancerColonSecondRecommendedText) {

		this.cancerWombInspectionItems = cancerWombInspectionItems;
		this.cancerWombResult = cancerWombResult;
		this.cancerWombDecision = cancerWombDecision;
		this.cancerWombRecommendedText = cancerWombRecommendedText;
		this.cancerLiverInspectionItems = cancerLiverInspectionItems;
		this.cancerLiverResult = cancerLiverResult;
		this.cancerLiverDecision = cancerLiverDecision;
		this.cancerLiverRecommendedText = cancerLiverRecommendedText;
		this.cancerStomachInspectionItems = cancerStomachInspectionItems;
		this.cancerStomachResult = cancerStomachResult;
		this.cancerStomachDecision = cancerStomachDecision;
		this.cancerStomachRecommendedText = cancerStomachRecommendedText;
		this.cancerColonInspectionItems = cancerColonInspectionItems;
		this.cancerColonResult = cancerColonResult;
		this.cancerColonDecision = cancerColonDecision;
		this.cancerColonRecommendedText = cancerColonRecommendedText;
		this.cancerBreastInspectionItems = cancerBreastInspectionItems;
		this.cancerBreastResult = cancerBreastResult;
		this.cancerBreastDecision = cancerBreastDecision;
		this.cancerBreastRecommendedText = cancerBreastRecommendedText;
		this.cancerLiverSecondInspectionItems = cancerLiverSecondInspectionItems;
		this.cancerLiverSecondResult = cancerLiverSecondResult;
		this.cancerLiverSecondDecision = cancerLiverSecondDecision;
		this.cancerLiverSecondRecommendedText = cancerLiverSecondRecommendedText;
		this.cancerLungInspectionItems = cancerLungInspectionItems;
		this.cancerLungResult = cancerLungResult;
		this.cancerLungDecision = cancerLungDecision;
		this.cancerLungRecommendedText = cancerLungRecommendedText;
		this.cancerColonSecondInspectionItems = cancerColonSecondInspectionItems;
		this.cancerColonSecondResult = cancerColonSecondResult;
		this.cancerColonSecondDecision = cancerColonSecondDecision;
		this.cancerColonSecondRecommendedText = cancerColonSecondRecommendedText;
	}
}
