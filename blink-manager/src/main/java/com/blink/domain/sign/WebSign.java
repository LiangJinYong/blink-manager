package com.blink.domain.sign;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class WebSign extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String doctorName;

	@Column(length = 45)
	private String doctorPhone;

	private String doctorId;

	private String groupId;

	private String license;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@Builder
	public WebSign(String doctorName, String doctorPhone, String doctorId, String license, Hospital hospital,
			String groupId) {
		this.doctorName = doctorName;
		this.doctorPhone = doctorPhone;
		this.doctorId = doctorId;
		this.license = license;
		this.hospital = hospital;
		this.groupId = groupId;
	}
}
