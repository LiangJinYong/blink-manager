package com.blink.domain.joinContact;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.blink.domain.BaseTimeEntity;
import com.blink.enumeration.VisitAim;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "joincontact")
public class JoinContact extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String clinicName;

	private String email;

	private String name;

	private LocalDateTime regDate = LocalDateTime.now();

	private String tel;

	@Column(columnDefinition = "text")
	private String inquiry;

	private String usedProgram;

	@Column(columnDefinition = "text")
	private String answerContent;

	private Boolean answerYn = false;

	private Boolean visitReserveYn;

	private LocalDateTime visitDate;

	@Enumerated(EnumType.STRING)
	private VisitAim visitAim;

	public void answer(String answerContent, Boolean visitReserveYn, LocalDateTime visitDate, VisitAim visitAim) {
		this.answerYn = true;
		this.answerContent = answerContent;
		this.visitReserveYn = visitReserveYn;
		this.visitDate = visitDate;
		this.visitAim = visitAim;
	}

	@Builder
	public JoinContact(String clinicName, String email, String name, String tel, String inquiry, String usedProgram) {
		this.clinicName = clinicName;
		this.email = email;
		this.name = name;
		this.tel = tel;
		this.inquiry = inquiry;
		this.usedProgram = usedProgram;
	}
}
