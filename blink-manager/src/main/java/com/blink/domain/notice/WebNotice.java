package com.blink.domain.notice;

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
public class WebNotice extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(columnDefinition = "text")
	private String description;
	
	@Column(length=45)
	private String groupId;
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@Builder
	public WebNotice(String title, String description) {
		this.title = title;
		this.description = description;
	}
}
