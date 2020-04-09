package com.blink.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserParserRequestDto {

	private UserDataRequestDto userData;
	private UserExaminationMetadataRequestDto userExaminationMetadata;
}
