package com.blink.domain.admin;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.judge.WebJudge;
import com.blink.enumeration.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Admin extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 64)
	private String email;

	@Column(length = 32, unique = true)
	private String name;

	@Column(nullable = false)
	private String password;

	private LocalDateTime regDate = LocalDateTime.now();

	@Column(nullable = false, columnDefinition = "bit(1)")
	private Integer reqChange = 1;

	@Enumerated(EnumType.ORDINAL)
	private Role roleId = Role.HOSPITAL;

	private Integer loginTryCnt = 0;

	@Column(nullable = false, columnDefinition = "tinyint(1) unsigned")
	private Integer accountStatus = 0;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@OneToOne(mappedBy = "user")
	private WebJudge webJudge;

	@Builder
	public Admin(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public void resetLoginTryCount() {
		loginTryCnt = 0;
	}

	public void increaseLoginTryCount() {
		loginTryCnt++;
	}
	
	public void setTempPassword(String tempPassword) {
		this.password = tempPassword;
	}
	
	public void modifyEmail(String newEmail) {
		this.email = newEmail;
	}
}
