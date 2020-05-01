package com.blink.domain.agreeUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "web_agree_user_list")
public class AgreeUserList extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "hospital_id")
	private Hospital hospital;

	@Column(length = 45)
	private String groupId;
	
	private Integer count;
	
	@Builder
	public AgreeUserList(Hospital hospital, String groupId) {
		this.hospital = hospital;
		this.groupId = groupId;
	}
}
