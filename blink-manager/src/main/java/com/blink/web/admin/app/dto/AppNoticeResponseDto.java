package com.blink.web.admin.app.dto;

import java.time.LocalDateTime;

import com.blink.domain.app.notice.AppNotice;

import lombok.Getter;

@Getter
public class AppNoticeResponseDto {

	private Long id;
	private String title;
	private String description;
	private LocalDateTime createdAt;
	
	public AppNoticeResponseDto(AppNotice entity) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.description = entity.getDescription();
		this.createdAt = entity.getCreatedAt();
	}
}
