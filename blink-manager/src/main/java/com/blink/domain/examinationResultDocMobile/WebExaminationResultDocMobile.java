package com.blink.domain.examinationResultDocMobile;

import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.hospital.Hospital;
import com.blink.domain.user.UserExaminationMetadata;
import com.blink.domain.user.UserInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "web_examination_result_doc_mobile")
public class WebExaminationResultDocMobile extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String groupId;

	@JsonIgnore
	@JoinColumn(name = "hospital_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@ManyToOne(fetch = FetchType.LAZY)
	private Hospital hospital;

	@JsonIgnore
	@JoinColumn(name = "user_info_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@ManyToOne(fetch = FetchType.LAZY)
	private UserInfo userInfo;

	@JsonIgnore
	@JoinColumn(name = "metadata_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT, name = "NONE"))
	@ManyToOne(fetch = FetchType.LAZY)
	private UserExaminationMetadata userExaminationMetadata;

	private Integer status;
	
	private String hospitalName;
	
	private String hospitalAddress;
	
	private String hospitalPostcode;
}
