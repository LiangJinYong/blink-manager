package com.blink.web.admin.web.dto.joinConcact;

import java.time.LocalDateTime;

import com.blink.enumeration.VisitAim;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SingleJoinConcactResponseDto {

	private final Long joinContactId;
	private final String clinicName;
	private final LocalDateTime regDate;
	private final String name;
	private final String tel;
	private final Boolean answerYn;
	private final String inquiry;
	private final String usedProgram;
	private final String email;
	private final String answerContent;
	private final Boolean visitReserveYn;
	private final LocalDateTime visitDate;
	private final VisitAim visitAim;
}
