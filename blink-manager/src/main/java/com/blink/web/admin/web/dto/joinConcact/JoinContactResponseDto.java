package com.blink.web.admin.web.dto.joinConcact;

import org.springframework.data.domain.Page;

import com.blink.domain.joinContact.JoinContact;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JoinContactResponseDto {

	private final Long joinContactCount;
	private final Long unansweredCount;
	private final Long answeredCount;
	private final String mostUsedProgram;
	private final Page<JoinContact> joinConcactList;
}
