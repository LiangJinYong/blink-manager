package com.blink.web.admin.web.dto;

import com.blink.domain.requiredNotice.WebRequiredNotice;

import lombok.Data;

@Data
public class WebRequiredNoticeSaveRequestDto {

	private String title1;
	private String title2;
	private String title3;
	private String title4;
	private String title5;
	private String title6;
	private String title7;
	private String title8;
	private String title9;
	private String descr1;
	private String descr2;
	private String descr3;
	private String descr4;
	private String descr5;
	private String descr6;
	private String descr7;
	private String descr8;
	private String descr9;
	private String version;

	public WebRequiredNotice toEntity() {
		return WebRequiredNotice.builder() //
				.title1(title1) //
				.title2(title2) //
				.title3(title3) //
				.title4(title4) //
				.title5(title5) //
				.title6(title6) //
				.title7(title7) //
				.title8(title8) //
				.title9(title9) //
				.descr1(descr1) //
				.descr2(descr2) //
				.descr3(descr3) //
				.descr4(descr4) //
				.descr5(descr5) //
				.descr6(descr6) //
				.descr7(descr7) //
				.descr8(descr8) //
				.descr9(descr9) //
				.version(version) //
				.build();
	}
}
