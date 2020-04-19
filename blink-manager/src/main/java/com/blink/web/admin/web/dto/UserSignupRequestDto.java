package com.blink.web.admin.web.dto;

import com.blink.domain.admin.Admin;
import com.blink.domain.hospital.Hospital;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserSignupRequestDto {

	private String username;
	private String password;
	private String hospitalName;
	private String hospitalTel;
	private String postcode;
	private String address;
	private String addressDetail;
	private String employeeName;
	private String employeePosition;
	private String employeeTel;
	private String employeeEmail;
	private Integer agreeSendYn;
	private String programInUse;

	public Admin toAdminEntity(String username, String password, String employeeEmail) {
		return Admin.builder().name(username).password(password).email(employeeEmail).build();
	}

	public Hospital toHospitalEntity(String hospitalName, String hospitalTel, String postcode, String address,
			String addressDetail, String employeeName, String employeePosition, String employeeTel,
			String employeeEmail, Integer agreeSendYn, String programInUse) {
		return Hospital.builder().displayName(hospitalName)
								.tel(hospitalTel)
								.postcode(postcode)
								.address(address)
								.addressDetail(addressDetail)
								.employeeName(employeeName)
								.employeePosition(employeePosition)
								.employeeTel(employeeTel).employeeEmail(employeeEmail)
								.agreeSendYn(agreeSendYn)
								.name(username)
								.programInUse(programInUse).build();
	}

	@Builder
	public UserSignupRequestDto(String username, String password, String hospitalName, String hospitalTel,
			String postcode, String address, String addressDetail, String employeeName, String employeePosition,
			String employeeTel, String employeeEmail, Integer agreeSendYn, String programInUse) {
		this.username = username;
		this.password = password;
		this.hospitalName = hospitalName;
		this.hospitalTel = hospitalTel;
		this.postcode = postcode;
		this.address = address;
		this.addressDetail = addressDetail;
		this.employeeName = employeeName;
		this.employeePosition = employeePosition;
		this.employeeTel = employeeTel;
		this.employeeEmail = employeeEmail;
		this.agreeSendYn = agreeSendYn;
		this.programInUse = programInUse;
	}
}
