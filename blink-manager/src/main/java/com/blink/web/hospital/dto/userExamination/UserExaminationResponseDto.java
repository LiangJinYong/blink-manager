package com.blink.web.hospital.dto.userExamination;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserExaminationResponseDto {

	private final Integer totalUserExaminationCount;
	private final Integer firstExaminationCount;
	private final Integer secondExaminationCount;
	private final Integer thirdExaminationCount;
	private final Integer nonexistConsetFormCount;
	
	private final Page<SingleUserExaminationResponseDto> userExaminationList;
}
