package com.blink.web.admin.web.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.blink.domain.user.UserData;
import com.blink.enumeration.Gender;
import com.blink.enumeration.Nationality;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataRequestDto {

	private String name;
	private Integer nationality;
	private String phone;
	private String birthday;
	private Integer gender;
	private String ssnPartial;
	
	@Builder
	public UserDataRequestDto(String name, Integer nationality, String phone, String birthday, Integer gender, String ssnPartial) {
		this.name =  name;
		this.nationality = nationality;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;
		this.ssnPartial = ssnPartial;
	}
	
	public UserData toEntity() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		return UserData.builder()
			.name(name)
			.nationality(Nationality.values()[nationality])
			.phone(phone)
			.birthday(LocalDate.parse(birthday, formatter)) 
			.gender(Gender.values()[gender])
			.ssnPartial(ssnPartial)
			.build();
			
	}
}
