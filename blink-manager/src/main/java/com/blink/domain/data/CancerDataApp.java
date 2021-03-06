package com.blink.domain.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.blink.domain.user.UserExaminationEntireDataOfOne;
import com.blink.enumeration.CancerAnalyzerStatus;
import com.blink.enumeration.CancerType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CancerDataApp implements Serializable {

	private static final long serialVersionUID = 1L;

	public CancerDataApp() {
	}

	public CancerDataApp(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@JoinColumn(name = "user_examination_entire_data_of_one_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@ManyToOne(fetch = FetchType.LAZY)
	private UserExaminationEntireDataOfOne userExaminationEntireDataOfOne;

	@Column(columnDefinition = "varchar(255) comment '판정(조건1)'")
	private String caseCondition1;

	@Column(columnDefinition = "varchar(255) comment '조건2'")
	private String caseCondition2;

	@Column(columnDefinition = "varchar(255) comment '조건3'")
	private String caseCondition3;

	@Column(columnDefinition = "varchar(255) comment '조건4'")
	private String caseCondition4;

	private String title;
	private String description;
	private String childLeft;
	private String childDescription;
	private String childRight;
	private String comment;
	private String postComment;
	private String preComment;

	@Enumerated(EnumType.ORDINAL)
	@Column(columnDefinition = "tinyint comment '분석기 상태'")
	private CancerAnalyzerStatus analyzerStatus;

	@Column(columnDefinition = "varchar(20) comment '이전암환자'")
	private String hadCancer;

	@Column(columnDefinition = "varchar(20) comment '종류'")
	@Enumerated(EnumType.STRING)
	private CancerType type;

	@Builder
	public CancerDataApp(UserExaminationEntireDataOfOne userExaminationEntireDataOfOne, String caseCondition1,
			String caseCondition2, String caseCondition3, String caseCondition4, String title, String description,
			String childLeft, String childDescription, String childRight, String comment, String postComment,
			String preComment, CancerAnalyzerStatus analyzerStatus, String hadCancer, CancerType type) {
		this.userExaminationEntireDataOfOne = userExaminationEntireDataOfOne;
		this.caseCondition1 = caseCondition1;
		this.caseCondition2 = caseCondition2;
		this.caseCondition3 = caseCondition3;
		this.caseCondition4 = caseCondition4;
		this.title = title;
		this.description = description;
		this.childLeft = childLeft;
		this.childDescription = childDescription;
		this.childRight = childRight;
		this.comment = comment;
		this.postComment = postComment;
		this.preComment = preComment;
		this.analyzerStatus = analyzerStatus;
		this.hadCancer = hadCancer;
		this.type = type;
	}

	public void update(String caseCondition1, String caseCondition2, String caseCondition3, String caseCondition4,
			String title, String description, String childLeft, String childDescription, String childRight,
			String comment, String postComment, String preComment, CancerAnalyzerStatus analyzerStatus,
			String hadCancer) {
		this.caseCondition1 = caseCondition1;
		this.caseCondition2 = caseCondition2;
		this.caseCondition3 = caseCondition3;
		this.caseCondition4 = caseCondition4;
		this.title = title;
		this.description = description;
		this.childLeft = childLeft;
		this.childDescription = childDescription;
		this.childRight = childRight;
		this.comment = comment;
		this.postComment = postComment;
		this.preComment = preComment;
		this.analyzerStatus = analyzerStatus;
		this.hadCancer = hadCancer;

	}
}
