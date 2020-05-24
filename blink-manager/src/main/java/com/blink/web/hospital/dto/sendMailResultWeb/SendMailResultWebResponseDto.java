package com.blink.web.hospital.dto.sendMailResultWeb;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SendMailResultWebResponseDto {

	private final Long id;
	private final String filekey;
	private final String filename;
	private final LocalDateTime createdAt;
	private final LocalDate sendDate;
	private final LocalDate uploadDate;
	private final Integer sentCount;
	
	private String sendStatus;
}
