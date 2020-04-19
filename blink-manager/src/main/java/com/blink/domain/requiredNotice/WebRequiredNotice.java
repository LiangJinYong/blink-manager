package com.blink.domain.requiredNotice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.blink.domain.BaseTimeEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class WebRequiredNotice extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "title_1")
	private String title1;

	@Column(name = "descr_1", columnDefinition = "text")
	private String descr1;

	@Column(name = "title_2")
	private String title2;

	@Column(name = "descr_2", columnDefinition = "text")
	private String descr2;

	@Column(name = "title_3")
	private String title3;

	@Column(name = "descr_3", columnDefinition = "text")
	private String descr3;

	@Column(name = "title_4")
	private String title4;

	@Column(name = "descr_4", columnDefinition = "text")
	private String descr4;

	@Column(name = "title_5")
	private String title5;

	@Column(name = "descr_5", columnDefinition = "text")
	private String descr5;

	@Column(name = "title_6")
	private String title6;

	@Column(name = "descr_6", columnDefinition = "text")
	private String descr6;

	@Column(name = "title_7")
	private String title7;

	@Column(name = "descr_7", columnDefinition = "text")
	private String descr7;

	@Column(name = "title_8")
	private String title8;

	@Column(name = "descr_8", columnDefinition = "text")
	private String descr8;

	@Column(name = "title_9")
	private String title9;

	@Column(name = "descr_9", columnDefinition = "text")
	private String descr9;

	private String version;

	@Builder
	public WebRequiredNotice(String title1, String title2, String title3, String title4, String title5, String title6,
			String title7, String title8, String title9, String descr1, String descr2, String descr3, String descr4,
			String descr5, String descr6, String descr7, String descr8, String descr9, String version) {
		this.title1 = title1;
		this.title2 = title2;
		this.title3 = title3;
		this.title4 = title4;
		this.title5 = title5;
		this.title6 = title6;
		this.title7 = title7;
		this.title8 = title8;
		this.title9 = title9;

		this.descr1 = descr1;
		this.descr2 = descr2;
		this.descr3 = descr3;
		this.descr4 = descr4;
		this.descr5 = descr5;
		this.descr6 = descr6;
		this.descr7 = descr7;
		this.descr8 = descr8;
		this.descr9 = descr9;

		this.version = version;

	}
}
