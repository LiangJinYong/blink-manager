package com.blink.domain.judge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;
import com.blink.enumeration.JudgeStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class WebJudge extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@Enumerated(EnumType.STRING)
	private JudgeStatus judgeStatus;

	@Column(columnDefinition = "text")
	private String rejectMsg;
	
	@Builder
	public WebJudge(Hospital hospital, JudgeStatus judgeStatus) {
		this.hospital = hospital;
		this.judgeStatus = judgeStatus;
	}

	public void changeJudgeStatus(JudgeStatus judgeStatus, String rejectMsg) {
		this.judgeStatus = judgeStatus;
		this.rejectMsg = rejectMsg;
	}
}
