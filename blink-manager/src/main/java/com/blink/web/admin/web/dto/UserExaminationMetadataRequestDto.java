package com.blink.web.admin.web.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.blink.domain.user.UserExaminationMetadata;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserExaminationMetadataRequestDto {

	private Integer agreeYn;
	private String dateExamined;
	private Long hospitalDataId;
	private Integer agreeMail;
	private Integer agreeSms;
	private Integer agreeVisit;
	private Integer examinationYear;
	
	@Builder
	public UserExaminationMetadataRequestDto(Integer agreeYn, String dateExamined, Long hospitalDataId, Integer agreeMail, Integer agreeSms, Integer agreeVisit, Integer examinationYear) {
		this.agreeYn = agreeYn;
		this.dateExamined = dateExamined;
		this.hospitalDataId = hospitalDataId;
		this.agreeMail = agreeMail;
		this.agreeSms = agreeSms;
		this.agreeVisit = agreeVisit;
		this.examinationYear = examinationYear;
	}
	
	public UserExaminationMetadata toEntity() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		return UserExaminationMetadata.builder()
				.agreeYN(agreeYn)
				.dateExamined(LocalDate.parse(dateExamined, formatter))
				.hospitalDataId(hospitalDataId)
				.agreeMail(agreeMail)
				.agreeSms(agreeSms)
				.agreeVisit(agreeVisit)
				.examinationYear(examinationYear)
				.build();
	}
}


