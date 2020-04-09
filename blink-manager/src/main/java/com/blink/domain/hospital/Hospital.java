package com.blink.domain.hospital;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.blink.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "hospital")
public class Hospital extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String address;

	private String addressDetail;

	@Column(length = 5)
	private String postcode;

	private String displayName;

	private String tel;

	private String employeeEmail;

	private String employeeName;

	private String employeeTel;

	private String employeePosition;

	@Column(nullable = false)
	private Integer isDelete = 0;

	private String name;

	private LocalDateTime regDate = LocalDateTime.now();

	private Long roleId = 2L;

	@Column(nullable = false)
	private Integer agreenSendYn = 1;

	private String programInUse;

	@Column(nullable = false)
	private Integer accountStatus = 0;

	@Builder
	public Hospital(String displayName, String tel, String postcode, String address, String addressDetail, String employeeName, String employeePosition, String employeeTel, String employeeEmail, Integer agreenSendYn, String name, String programInUse) {
		this.displayName = displayName;
		this.tel = tel;
		this.postcode = postcode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.employeeName = employeeName;
		this.employeePosition = employeePosition;
		this.employeeTel = employeeTel;
		this.employeeEmail = employeeEmail;
		this.agreenSendYn = agreenSendYn;
		this.name = name;
		this.programInUse = programInUse;
	}
}
