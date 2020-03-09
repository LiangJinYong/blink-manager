package com.blink.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "hospital")
public class Hospital {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime createdAt = LocalDateTime.now();

	private LocalDateTime updatedAt;

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

	private Long roleId;

	@Column(nullable = false)
	private int agreenSendYn;

	private String programInUse;

	@Column(nullable = false)
	private Integer accountStatus = 0;

	private Integer filesId;
}
