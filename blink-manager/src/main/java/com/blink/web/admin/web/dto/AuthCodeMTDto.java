package com.blink.web.admin.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthCodeMTDto {
	private String authCode;
	private String phoneNumber;
}
