package com.blink.domain.user;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.blink.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserExaminationMetadata extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String address;
	
	@Column(name="agree_yn", columnDefinition="tinyint")
	private Integer agreeYN;
	
	private LocalDate dateExamined;

	@Column(nullable = false, columnDefinition="smallint")
	private Integer examinationYear;
	
	private Long hospitalDataId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="user_data_id")
	private UserData userData;
	
	private Integer agreeMail;
	
    private Integer agreeSms;
    
    private Integer agreeVisit;
    
    public void setUserData(UserData userData) {
		this.userData = userData;
	}

    @Builder
    public UserExaminationMetadata(Integer agreeYN, LocalDate dateExamined, Long hospitalDataId, Integer agreeMail, Integer agreeSms, Integer agreeVisit, Integer examinationYear) {
    	this.agreeYN = agreeYN;
    	this.dateExamined = dateExamined;
    	this.hospitalDataId = hospitalDataId;
    	this.agreeMail = agreeMail;
    	this.agreeSms = agreeSms;
    	this.agreeVisit = agreeVisit;
    	this.examinationYear = examinationYear;
    }

	public void update(Integer agreeYn, LocalDate dateExamined, Long hospitalDataId, Integer agreeMail, Integer agreeSms,
			Integer agreeVisit, Integer examinationYear) {
		
		this.agreeYN = agreeYn;
		this.dateExamined = dateExamined;
		this.hospitalDataId = hospitalDataId;
		this.agreeMail = agreeMail;
		this.agreeSms = agreeSms;
		this.agreeVisit = agreeVisit;
		this.examinationYear = examinationYear;
		
	}
}
