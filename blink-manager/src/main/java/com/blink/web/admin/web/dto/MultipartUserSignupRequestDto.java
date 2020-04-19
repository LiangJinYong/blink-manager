package com.blink.web.admin.web.dto;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.blink.domain.admin.Admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MultipartUserSignupRequestDto {
	
	@NotEmpty
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
	private MultipartFile file;
	
	public Admin toAdminEntity(String username, String password, String employeeEmail) {
		return Admin.builder().name(username).password(password).email(employeeEmail).build();
	}
}
