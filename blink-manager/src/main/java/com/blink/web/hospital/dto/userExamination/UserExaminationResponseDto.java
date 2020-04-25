package com.blink.web.hospital.dto.userExamination;

import org.springframework.data.domain.Page;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserExaminationResponseDto {

	private final Page<SingleUserExaminationResponseDto> userExaminationList;
}
