package com.blink.web.admin.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class HospitalResponseDto {

	private String hospitalName;
	private String hospitalId;
	private String contractDate;
	private String employeeName;
	private String contact;
	private Integer agreenSendYn;
	private String fullAddress;
	private String employeeEmail;
	
	public HospitalResponseDto(String hospitalName, String hospitalId, String contractDate, String employeeName, String contact, Integer agreenSendYn, String fullAddress, String employeeEmail) {
		this.hospitalName = hospitalName;
		this.hospitalId = hospitalId;
		this.contractDate = contractDate;
		this.employeeName = employeeName;
		this.contact = contact;
		this.agreenSendYn = agreenSendYn;
		this.fullAddress = fullAddress;
		this.employeeEmail = employeeEmail;
	}
}