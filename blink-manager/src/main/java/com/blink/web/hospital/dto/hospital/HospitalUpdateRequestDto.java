package com.blink.web.hospital.dto.hospital;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
public class HospitalUpdateRequestDto {

	private Long hospitalId;
	private String hospitalName;
	private String employeeName;
	private String hospitalTel;
	private String employeeTel;
	private Integer agreeSendYn;
	private String postcode;
	private String address;
	private String addressDetail;
	private String employeeEmail;
	private String programInUse;
	private Integer signagesStand;
	private Integer signagesMountable;
	private Integer signagesExisting;
	private String groupFileId;
	private String employeePosition;
	private String password;

	@Builder
	public HospitalUpdateRequestDto(Long hospitalId, String hospitalName, String employeeName, String hospitalTel,
			String employeeTel, Integer agreeSendYn, String postcode, String address, String addressDetail,
			String employeeEmail, String programInUse, Integer signagesStand, Integer signagesMountable,
			Integer signagesExisting, String groupFileId, String employeePosition, String password) {
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.employeeName = employeeName;
		this.hospitalTel = hospitalTel;
		this.employeeTel = employeeTel;
		this.agreeSendYn = agreeSendYn;
		this.postcode = postcode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.employeeEmail = employeeEmail;
		this.programInUse = programInUse;
		this.signagesStand = signagesStand;
		this.signagesMountable = signagesMountable;
		this.signagesExisting = signagesExisting;
		this.groupFileId = groupFileId;
		this.employeePosition = employeePosition;
		this.password = password;
	}
}
