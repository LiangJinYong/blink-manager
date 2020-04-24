package com.blink.web.admin.web.dto.joinConcact;

import java.time.LocalDateTime;

import com.blink.enumeration.VisitAim;

import lombok.Data;

@Data
public class JoinContactAnswerRequestDto {

	private Long id;
	private String answerContent;
	private boolean visitReserveYn;
	private LocalDateTime visitDate;
	private VisitAim visitAim;
}
