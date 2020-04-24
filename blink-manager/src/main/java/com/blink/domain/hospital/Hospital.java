package com.blink.domain.hospital;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.blink.domain.BaseTimeEntity;
import com.blink.domain.admin.Admin;
import com.blink.domain.agreeUser.AgreeUserList;
import com.blink.domain.consentForm.WebConsentForm;
import com.blink.domain.examinationResultDoc.WebExaminationResultDoc;
import com.blink.domain.judge.WebJudge;
import com.blink.domain.qna.WebQna;
import com.blink.enumeration.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
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

	@Enumerated(EnumType.ORDINAL)
	private Role roleId = Role.HOSPITAL;

	@Column(nullable = false, columnDefinition = "bit(1)")
	private Integer agreeSendYn;

	private String programInUse;

	@OneToOne(mappedBy = "hospital")
	private Admin admin;

	@Column(length = 45, nullable = false)
	private String groupId;

	private Integer signagesStand;

	private Integer signagesMountable;

	private Integer signagesExisting;

	@OneToOne(mappedBy = "hospital")
	private WebJudge webJudge;

	@OneToMany(mappedBy = "hospital")
	private List<WebQna> webQnaList;

	@OneToMany(mappedBy = "hospital")
	private List<AgreeUserList> agreeUserLists;
	
	@OneToMany(mappedBy = "hospital")
	private List<WebExaminationResultDoc> webExaminationResultDocList;
	
	@OneToMany(mappedBy = "hospital")
	private List<WebConsentForm> webConsentFormList;

	public void assignGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Builder
	public Hospital(String displayName, String tel, String postcode, String address, String addressDetail,
			String employeeName, String employeePosition, String employeeTel, String employeeEmail, Integer agreeSendYn,
			String name, String programInUse, String groupId) {
		this.displayName = displayName;
		this.tel = tel;
		this.postcode = postcode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.employeeName = employeeName;
		this.employeePosition = employeePosition;
		this.employeeTel = employeeTel;
		this.employeeEmail = employeeEmail;
		this.agreeSendYn = agreeSendYn;
		this.name = name;
		this.programInUse = programInUse;
		this.groupId = groupId;
	}

	public void modifyEmaail(String newEmail) {
		this.employeeEmail = newEmail;
	}

	public void updateHospitalInfo(String hospitalName, String employeeTel, String address, String addressDetail,
			String postcode, String employeeEmail, String employeePosition, String employeeName, Integer agreeSendYn,
			String programInUse, Integer signagesStand, Integer signagesMountable, Integer signagesExisting) {
		this.displayName = hospitalName;
		this.employeeTel = employeeTel;
		this.address = address;
		this.addressDetail = addressDetail;
		this.postcode = postcode;
		this.employeeEmail = employeeEmail;
		this.employeePosition = employeePosition;
		this.employeeName = employeeName;
		this.agreeSendYn = agreeSendYn;
		this.programInUse = programInUse;
		this.signagesStand = signagesStand;
		this.signagesMountable = signagesMountable;
		this.signagesExisting = signagesExisting;
	}
	
	public void setDelete() {
		this.isDelete = 1;
	}

}
