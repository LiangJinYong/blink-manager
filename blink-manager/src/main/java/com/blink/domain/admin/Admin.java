package com.blink.domain.admin;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;

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

	private String password;

	private LocalDateTime regDate = LocalDateTime.now();

	private Integer reqChange = 1;

	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	private Long roleId = 2L;

	@Column(length = 1)
	private Integer loginTryCnt = 0;
	
	@Builder
	public Admin(String email, String name, String password) {
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
}
