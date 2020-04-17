package com.blink.web.admin.app.dto;

import com.blink.domain.app.notice.AppNotice;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AppNoticeSaveRequestDto {
	
	private String title;
	
	private String description;
	
	@Builder
	public AppNoticeSaveRequestDto(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public AppNotice toEntity() {
		return AppNotice.builder()
			.title(title)
			.description(description)
			.build();
	}
}
