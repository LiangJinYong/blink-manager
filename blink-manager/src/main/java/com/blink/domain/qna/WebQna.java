package com.blink.domain.qna;

import javax.persistence.Column;
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
import com.blink.enumeration.QuestionType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class WebQna extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	private String title;

	@Column(columnDefinition = "text")
	private String questionContent;

	@Column(length = 45)
	private String questionGroupId;

	@Column(columnDefinition = "bit(1)")
	private boolean answerYn;

	@Column(columnDefinition = "text")
	private String answerContent;

	@Column(length = 45)
	private String answerGroupId;
	
	@Column(columnDefinition = "bit(1)")
	private boolean readAnswerYn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;
	
	@Builder
	public WebQna(Hospital hospital, QuestionType questionType, String title, String questionContent) {
		this.hospital = hospital;
		this.questionType = questionType;
		this.title = title;
		this.questionContent = questionContent;
	}
	
	public void setQuestionGroupId(String questionGroupId) {
		this.questionGroupId = questionGroupId;
	}
	
	public void setAnswerGroupId(String answerGroupId) {
		this.answerGroupId = answerGroupId;
	}

	public void completeAnswer(String answerContent) {
		this.answerContent = answerContent;
		this.answerYn = true;
	}
}
