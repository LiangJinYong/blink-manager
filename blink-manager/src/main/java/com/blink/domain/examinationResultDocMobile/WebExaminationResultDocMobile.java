package com.blink.domain.examinationResultDocMobile;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserExaminationMetadata userExaminationMetadata;

	private Integer status;

	private String hospitalName;

	private String hospitalAddress;

	private String hospitalPostcode;

	private Integer resultStatus;

	private String pushMsg;

	@Column(name = "app_msg_1", columnDefinition = "varchar(500)")
	private String appMsg1;

	@Column(name = "app_msg_2", columnDefinition = "varchar(500)")
	private String appMsg2;

	public void update(Hospital hospital, UserExaminationMetadata userExaminationMetadata, Integer status,
			String hospitalName, String hospitalAddress, String hospitalPostcode, Integer resultStatus, String pushMsg,
			String appMsg1, String appMsg2) {
		this.hospital = hospital;
		this.userExaminationMetadata = userExaminationMetadata;
		this.status = status;
		this.hospitalName = hospitalName;
		this.hospitalAddress = hospitalAddress;
		this.hospitalPostcode = hospitalPostcode;
		this.resultStatus = resultStatus;
		this.pushMsg = pushMsg;
		this.appMsg1 = appMsg1;
		this.appMsg2 = appMsg2;
	}
}
